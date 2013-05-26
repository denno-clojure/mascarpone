(ns orbit.jme.vector
  (:require [orbit.util :as util]
            [clojure.core :as clj]
            ; clojure.core.matrix
            ; (clojure.core.matrix
            ;   [implementations :as m-imp]
            ;   [protocols :as matrix-protocols]
            ;   [compliance-tester :as ct])
            )
  (:import (com.jme3.math Vector2f Vector3f Vector4f)
           (clojure.lang PersistentVector))
  (:refer-clojure :exclude [+ - * vector]))

(defn jvector? [v] (or (instance? Vector2f v)
                       (instance? Vector3f v)
                       (instance? Vector4f v)))

(defn ^Vector2f jvector2
  "Create a Vector2f."
  ([] Vector2f/ZERO)
  ([v] (cond
         (instance? Vector2f v) v
         (number? v) (Vector2f. v v)
         :else (util/convert-err v)))
  ([x y] (Vector2f. x y)))

(defn ^Vector3f jvector3
  "Create a Vector3f."
  ([] Vector3f/ZERO)
  ([v] (cond
         (instance? Vector3f v) v
         (number? v) (jvector3 v v v)
         :else (util/convert-err v)))
  ([x y z] (Vector3f. x y z)))

(defn ^Vector4f jvector4
  "Create a Vector4f."
  ([] Vector4f/ZERO)
  ([v] (cond
         (instance? Vector4f v) v
         (number? v) (jvector4 v v v v)
         :else (util/convert-err v)))
  ([x y z w] (Vector4f. x y z w)))

;; =====
;; Abstraction
;; =====
(defprotocol vector
  (add [v1 v2])
  (divide [v1 v2])
  (multiply [v1 v2])
  (negate [v])
  (subtract [v1 v2])
  (get-jme-vector [v])
  (get-v [v index])
  (set-v [v index value]))

(extend-type Vector2f
  vector
  (add [v1 v2]      (.add v1 v2))
  (divide [v1 v2]   (.divide v1 ^float v2))
  (multiply [v1 v2] (.mult v1 v2))
  (negate [v]       (.negate v))
  (subtract [v1 v2] (.subtract v1 v2))
  (get-jme-vector [v] v)
  (get-v [v ^long index]
    (case index
      0 (.getX v)
      1 (.getY v)))
  (set-v [v ^long index value]
    (case index
      0 (.setX v value)
      1 (.setY v value))))

(extend-type Vector3f
  vector
  (add [v1 v2]      (.add v1 v2))
  (divide [v1 v2]   (.divide v1 (jvector3 v2)))
  (multiply [v1 v2] (.mult v1 (jvector3 v2)))
  (negate [v]       (.negate v))
  (subtract [v1 v2] (.subtract v1 v2))
  (get-jme-vector [v] v)
  (get-v [v index] (.get v index))
  (set-v [v index value] (.set v index value)))

(extend-type Vector4f
  vector
  (add [v1 v2]      (.add v1 v2))
  (divide [v1 v2]   (.divide v1 (jvector4 v2)))
  (multiply [v1 v2] (.mult v1 (jvector4 v2)))
  (negate [v]       (.negate v))
  (subtract [v1 v2] (.subtract v1 v2))
  (get-jme-vector [v] v)
  (get-v [v index] (.get v index))
  (set-v [v index value] (.set v index value)))

(defn div-mult [v obj f]
  (cond
    (sequential? obj) (mapv f v obj)
    (number? obj) (mapv #(f % obj) v)
    :else (util/convert-err obj)))

(extend-type PersistentVector
  vector
  (add [v1 v2] (mapv clj/+ v1 v2))
  (divide [v obj] (div-mult v obj /))
  (multiply [v obj] (div-mult v obj clj/*))
  (negate [v] (mapv clj/- v))
  (subtract [v1 v2] (mapv clj/- v1 v2))
  (get-jme-vector [v]
    (case (count v)
      2 (jvector2 v)
      3 (jvector3 v)
      4 (jvector4 v)
      (util/convert-err v)))
  (get-v [v index] (nth v index))
  (set-v [v index value]
    (assoc-in v [index] value)))
 
(defn +
  ""
  ([v] v)
  ([v1 v2]
   (add v1 v2))
  ([v1 v2 v3 & more]
   (apply + (+ v1 v2) v3 more)))

(defn -
  ""
  ([v] (negate v))
  ([v1 v2]
   (subtract v1 v2)))

(defn *
  ""
  ([v] v)
  ([v1 obj]
   (multiply v1 obj))
  ([v1 v2 v3 & more]
   (apply * (* v1 v2) v3 more)))

(defn div
  ""
  ([v] v)
  ([v1 obj]
   (divide v1 obj)))

;; ======
;; Set/get
;; ======
(defn x
  "Get the x component of v."
  [v] (get-v v 0))

(defn x!
  "Set the x component of v to new-x."
  [v new-x] (set-v v 0 new-x))

(defn y
  "Get the y component of v."
  [v] (get-v v 1))

(defn y!
  "Set the y component of v to new-y"
  [v new-y] (set-v v 1 new-y))

(defn z
  "Get the z component of v."
  [v] (get-v v 2))

(defn z!
  "Set the z component of v to new-z."
  [v new-z] (set-v v 2 new-z))

(defn w
  "Get the w component of v."
  [v] (get-v v 3))

(defn w!
  "Set the w component of v to new-w"
  [v new-w] (set-v v 3 new-w))

(defn jvector
  "Create a Vector2f, Vector3f or Vector4f.
  
  The one argument version either takes an instance
  of the above, or a vector to convert."
  ([v]
   (cond
     (jvector? v) v
     (extends? vector v) (get-jme-vector v)
     (sequential? v) (apply jvector v)
     :else (util/convert-err v)))
  ([x y]     (jvector2 x y))
  ([x y z]   (jvector3 x y z))
  ([x y z w] (jvector4 x y z w)))

;; =====
;; clojure.core.matrix
;; =====
; (defrecord Vector0f [v])

; (defrecord Vector1f [v])

; (def jme-vector-implementation
;   (reify matrix-protocols/PImplementation
;     (implementation-key [m] ::jme-vector)
;     (construct-matrix [m data]
;       (let [dim (matrix-protocols/dimensionality data)]
;         (if (==  1)
;           (matrix-protocols/new-vector m dim)
;           (util/arg-err "cannot construct matrix.")
;           )))
;     (new-vector [m length]
;       (case (int length)
;         0 (Vector0f. 0)
;         1 (Vector1f. 0)
;         2 (Vector2f.)
;         3 (Vector3f.)
;         4 (Vector4f.)))
;     (new-matrix [m rows columns]
;       (util/arg-err "cannot construct matrix."))
;     (new-matrix-nd [m shape]
;       (if (== (count shape) 1)
;         (matrix-protocols/new-vector m (int (first shape)))
;         (util/arg-err "cannot construct matrix.")))
;     (supports-dimensionality? [m dimensions] (== dimensions 1))))

; (m-imp/register-implementation jme-vector-implementation)

; (defn compliance-test
;   "Runs the compliance test suite on a given matrix implementation.
; m can be either a matrix instance or the implementation keyword."
;   [m]
;   (let [im (m-imp/get-canonical-object m)
;         ik (m-imp/get-implementation-key im)]
;     (binding [clojure.core.matrix/*matrix-implementation* ik]
;       (ct/instance-test im)
;       ;(test-implementation im)
;       ;(test-assumptions-for-all-sizes im)
;       ;(test-coerce-via-vectors im)
;       ;(when (supports-dimensionality? im 2)
;       ;  (matrix-tests-2d im))
;       ;(when (supports-dimensionality? im 1)
;       ;  (vector-tests-1d im))
;       ;(test-array-interop im)
;       ;(test-numeric-functions im)
;       ;(test-dimensionality im)
;       ;(test-new-matrices im)
;       )))

; ;(compliance-test jme-vector-implementation)

; ;(clojure.core.matrix.compliance-tester/compliance-test jme-vector-implementation)

; ;(println (matrix-protocols/is-scalar? 3))
