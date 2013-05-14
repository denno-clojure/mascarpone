(ns quilltest.balls
  (:require [quilltest.core :as qcore]
            [quilltest.keys :as k]
            [quilltest.scene :as scene]
            [quilltest.physics :as p]
            [clojure.set :as set]
            [clojure.pprint :as pprint])
  (:require [quil.core :as q])
  (:gen-class))

;(-main)
(def params
  {:size [800 600]
   :fps 61
   :update-fps 500
   :balls 60})

(def state-atom
  (letfn [(px []
            [(rand-int (first (:size params)))
             (rand-int (second (:size params)))])]
      (atom
       (map (fn [id]
              {:pos (px)
               :velocity [0.5 0.5]
               :id id})
            (range (:balls params))))))

(def scene-graph
  (let [guy
        (scene/->GraphNode
         []
         (fn [[x y]]
           (q/ellipse x y 20 20)))]
    guy))

(def keys-atom (atom #{}))

(defn going-out? [pos max-pos vel]
  (or (and (> pos max-pos) (pos? vel))
      (and (< pos 0) (neg? vel))))

(defn bounds-check [state]
  ;(println state)
  (let [[max-x max-y] (:size params)
        [vx vy] (:velocity state)
        [x y] (:pos state)
        vx (if (going-out? x max-x vx) (- vx) vx)
        vy (if (going-out? y max-y vy) (- vy) vy)]
    [vx vy]))

(def scale 0.000001)
(def a 0.001)

(defn accelerate [t vx vy]
  ;; v = a*t + v0
  (let [step (* a t)
        pressed @keys-atom
        vx (if (pressed :a) (- vx step) vx)
        vx (if (pressed :d) (+ vx step) vx)
        vy (if (pressed :w) (- vy step) vy)
        vy (if (pressed :s) (+ vy step) vy)]
    [vx vy]))

(defn move [guy ticks]
  (let [t (* scale ticks)
        [vx vy] (bounds-check guy)
        [vx vy] (accelerate t vx vy)
        [vxt vyt] [(* vx t) (* vy t)]
        [x y] (:pos guy)]
    {;; x = vt + x0
     :pos [(+ x vxt) (+ y vyt)]
     :velocity [vx vy]}))

(defn setup []
  (q/frame-rate (:fps params))
  (q/stroke-weight 2)
  (q/smooth))

(defn draw []
  (q/background 100)
  (let [s @state-atom]
    (q/stroke 0)
    (dorun (map #(scene/draw scene-graph (:pos %)) s))))

(defn guy->rigidbody [guy]
  (let [{:keys [pos velocity]} guy]
    (p/->RigidBody
     1
     (p/->Vector2 (first pos) (second pos))
     (p/->Vector2 (first velocity) (second velocity)))))

(defn rigidbody->guy [rigidbody]
;  (println rigidbody)
  {:pos (let [pos (:position rigidbody)]
          [(:x pos) (:y pos)])
   :velocity (let [v (:velocity rigidbody)]
               [(:x v) (:y v)])})


(defn check-collisions
  "create a map of colliding guys"
  [guys]
  (let [combinations (for [x guys
                           y guys
                           ;; note, this implies ref semantics, not
                           ;; value, for equality, each game object
                           ;; should be unique
                           :when (not= x y)]
                       [x y])
        collided (filter (fn [[a b]]
                           (p/colliding? (:rigid-body a)
                                         (:rigid-body b)))
                         combinations)]
    (into {} collided)))

(defn update-rigidbodies
  [guys collided-pairs]
  ;; iterate over game objects, checking to see
  ;; if it's part of a collided pair, replacing it
  ;; with the updated values if it is
  ;; TODO: optimize, cache collided vals in a map
  (map 
   (fn [guy]
     (if-let [guy2 (collided-pairs guy)]
       (let [[rigidbody-a _] (p/collide (:rigid-body guy)
                                        (:rigid-body guy2))
             new-guy (assoc guy :rigid-body rigidbody-a)]
         new-guy)
       guy))
   guys))

(defn update-collisions [guys]
  (let [collided-pairs (check-collisions guys)
        updated (update-rigidbodies guys collided-pairs)]
;    (println collided-pairs)
    updated))

(defn update-guys
  "update the game state of the guys post-collision"
  [guys]
  (map
   (fn [guy]
     ;; if there is any state coming from the old guy, add
     ;; it here
     (let [new-state (rigidbody->guy (:rigid-body guy))]
       (merge guy new-state))) guys))

(defn update [guys ticks]
  (let [;;add a rigid-body key
        guys (map #(assoc % :rigid-body (guy->rigidbody %)) guys)
        guys (update-collisions guys)
        guys (update-guys guys)]
    (map #(move % ticks) guys)))

(defn -main []
  (qcore/run-sketch {:title "Balls"
                     :setup #'setup
                     :draw #'draw
                     :size (:size params)}
                    {:on-key-press (k/gen-on-keypress keys-atom)
                     :on-key-release (k/gen-on-keyrelease keys-atom)}
                    (:update-fps params)
                    (fn [ticks] (swap! state-atom #(update % ticks)))
                    #(when (:q @keys-atom) true)))

;;(-main)

