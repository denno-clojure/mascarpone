(ns quilltest.physics)

;;; Simple functions to calculate physics interactions


(defprotocol Mathy
  "A mathiness protocol"
  (plus [a b])
  (minus [a b])
  (dot [a b])
  (norm [this] "Takes the euclidian norm"))

(extend-protocol Mathy
  java.lang.Number
  (plus [a b] (+ a b))
  (minus [a b] (- a b))
  (dot [a b] (* a b))
  (norm [this] (Math/sqrt (* this this))))

(defmacro with-math [& body]
  `(let [~'+ ~(fn [& args] (reduce plus args))
         ~'- ~(fn [& args] (reduce minus args))
         ~'* ~(fn [& args] (reduce dot args))]
     ~@body))

(defprotocol Vectory
  (normalize [this])
  (scalar* [this s])
  (scalar+ [this s]))

(declare ->Vector2)
(defrecord Vector2 [x y]
  Mathy
  (plus [a b] (let [new-x (+ (:x a) (:x b))
                    new-y (+ (:y a) (:y b))]
                (->Vector2 new-x new-y)))
  (minus [a b] (let [new-x (- (:x a) (:x b))
                     new-y (- (:y a) (:y b))]
                 (->Vector2 new-x new-y)))
  (dot [a b] (+ (* (:x a) (:x b)) (* (:y a) (:y b))))
  (norm [this] (Math/sqrt (dot this this)))
  Vectory
  (normalize [this]
    (let [magnitude (norm this)]      
      (scalar* this (/ 1 magnitude))))
  (scalar* [this a]
    (->Vector2 (* a (:x this)) (* a (:y this))))
  (scalar+ [this a]
    (->Vector2 (+ a (:x this)) (+ a (:y this)))))

(defrecord RigidBody 
    [mass position velocity])

(defn approaching [r1 r2]
  (let [v1 (:velocity r1)
        v2 (:velocity r2)]
    (with-math (neg? (* v1 v2)))))

(defn colliding? [r1 r2]
  (and (approaching r1 r2)
    (let [x1 (:position r1)
          x2 (:position r2)
          [x1 y1] [(:x x1) (:y x1)]
          [x2 y2] [(:x x2) (:y x2)]
          [dx dy] [(- x1 x2) (- y1 y2)]
          distance (Math/sqrt (+ (* dy dy) (* dx dx)))]
      (< distance 20))))

(defn collide
  "Elastically collides two rigid bodies, returning
   their new velocities"
  [r1 r2]
  (with-math
    (let [[x1 x2] [(:position r1) (:position r2)]
          ;; vectors
          normal (normalize (- x1 x2))
;;          t (println "normal:" normal)      
          tangent (->Vector2 (- 0 (:y normal)) (:x normal))
;;          t (println "tangent:" tangent)          
          {m1 :mass v1 :velocity} r1
          {m2 :mass v2 :velocity} r2
          M (+ m1 m2)
          inv-M (/ 1 M)
;;          t (println 1)          
          ;; scalars
          [v1n v2n] [(* v1 normal)
                     (* v2 normal)]
;;          t (println "v1n v2n:" v1n v2n)
          ;; vectors
          [v1t v2t] [(scalar* tangent (* v1 tangent))
                     (scalar* tangent (* v2 tangent))]
;;          t (println "vtn v2t:" v1t v2t)
          v1n-prime (* inv-M (+
                              (* v1n (- m1 m2))
                              (* 2 m2 v2n)))
          v2n-prime (* inv-M (+
                              (* v2n (- m2 m1))
                              (* 2 m1 v1n)))
;;          t (println 1)          
          ;; tangential components do not change
          [v1 v2] [(+ (scalar* normal v1n-prime) v1t)
                   (+ (scalar* normal v2n-prime) v2t)]
          body1 (->RigidBody m1 x1 v1)
          body2 (->RigidBody m2 x2 v2)]
;;      (println r1 body1)
      [body1 body2])))

