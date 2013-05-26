(use 'clojure.core.contracts)

(def secure-doubler
  (with-constraints
    (fn [n] (* 2 n))
    (contract doubler
      "ensures doubling"
      [x] [number? => (= (* 2 x) %)]
      [x y] [(every? number? [x y])
               =>
             (= (* 2 (+ x y)) %)])))

(secure-doubler 10)