(defn circle-test [x y]
  (not ( > ( + (* (- x 0.5) (- x 0.5)) (* (- y 0.5) (- y 0.5))) 0.25)))

(defn error [est]
  (Math/abs (* (/ (- Math/PI est) Math/PI) 100)))

(defn pi [n]
  (loop [hits 0 total 0]
    (let [x (rand) y (rand)]
      (if (< total n)
        (recur (if (circle-test x y) (inc hits) hits) (inc total))
        (* (/ hits total) 4.0)))))

(defn estimate-pi [shots runs]
  (loop [r 0 vals ()]
    (let [est (pi shots)]
      (if (= r runs)
        (/ (reduce + vals) (count vals))
        (recur (inc r) (conj vals est))))))

(defn print-estimate [est]
  (println est)
  (println (str (error est) "%")))