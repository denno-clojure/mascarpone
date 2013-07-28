(ns demo)

(defn fib [n] 
  (take n 
    (map first (iterate (fn [[a b]] [b (+' a b)]) [0 1]))))

(defn -main[args &]	
   (fib (Integer. args)))