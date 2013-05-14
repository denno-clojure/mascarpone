(ns circles
  (:use quil.core
        [quil.helpers.calc :only [mod-range]]))

(def num 10)

(defn mk-circle []
  {:x (random (width))
   :y (random (height))
   :radius (+ 10 (random 100))
   :line-col (color (random 255) (random 255) (random 255))
   :fill-col (color (random 255) (random 255) (random 255))
   :alph (random 255)
   :xmove (- (random 10) 5)
   :ymove (- (random 10) 5)})

(defn add-circles
  [circles*]
  (dotimes [_ num]
    (let [c (mk-circle)]
      (swap! circles* conj c))))

(defn mouse-released
  []
  (add-circles (state :circles)))

(defn setup []
  (background 255)
  (smooth)
  (stroke-weight 1)
  (fill-int 150 50)
  (let [circles* (atom [])]
    (add-circles circles*)
    (set-state! :circles circles*)))

(defn update-circle
  [{:keys [x y xmove ymove radius] :as circle}]

  (let [new-x (+ x xmove)
        new-x (if (< new-x (- 0 radius)) (+ (width) radius) new-x)
        new-x (if (> new-x (+ (width) radius)) (- 0 radius) new-x)
        new-y (+ y ymove)
        new-y (if (< new-y (- 0 radius)) (+ (height) radius) new-y)
        new-y (if (> new-y (+ (height) radius)) (- 0 radius) new-y)]
    (assoc circle :x new-x :y new-y)))

(defn update-circles
  [circles]
  (map update-circle circles))

(defn draw-circle
  [{:keys [x y radius line-col fill-col alph]}]
  (no-stroke)
  (fill-int fill-col alph)
  (ellipse x y (* 2 radius) (* 2 radius))
  (stroke-int line-col 150)
  (no-fill)
  (ellipse x y 10 10))

(defn draw []
  (background 255)
  (let [circles* (state :circles)
        circles (swap! circles* update-circles)]
    (doseq [c circles]
      (draw-circle c))))

(defsketch gen-art-31
  :title "OO Circles"
  :setup setup
  :draw draw
  :mouse-released mouse-released
  :size [500 300])