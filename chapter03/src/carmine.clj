(require '[taoensso.carmine :as car])

(def pool         (car/make-conn-pool)) ; See docstring for additional options
(def spec-server1 (car/make-conn-spec)) ; ''

(defmacro wcar [& body] `(car/with-conn pool spec-server1 ~@body))

(wcar (car/ping)
      (car/set "foo" "bar")
      (car/get "foo"))

; pipelining
(wcar (car/set  "foo" "bar")
      (car/spop "foo")
      (car/get  "foo"))

; The only value type known to Redis internally is the byte string. 
; But Carmine uses Nippy under the hood and understands all of Clojure's 
; rich data types, letting you use them with Redis painlessly:
(wcar (car/set "clj-key" {:bigint (bigint 31415926535897932384626433832795)
                          :vec    (vec (range 5))
                          :set    #{true false :a :b :c :d}
                          :bytes  (byte-array 5)
                          ;; ...
                          })
      (car/get "clj-key"))

; commands are just functions
(wcar (doall (repeatedly 5 car/ping)))
; => ["PONG" "PONG" "PONG" "PONG" "PONG"]

(let [first-names ["Salvatore"  "Rich"]
      surnames    ["Sanfilippo" "Hickey"]]
  (wcar (doall (map #(car/set %1 %2) first-names surnames))
        (doall (map car/get first-names))))
; => ["OK" "OK" "Sanfilippo" "Hickey"]

(let [hash-key "awesome-people"]
  (wcar (car/hmset hash-key "Rich" "Hickey" "Salvatore" "Sanfilippo")
        (doall (map (partial car/hget hash-key)
                    ;; Execute with own connection & pipeline then return result
                    ;; for composition:
                    (wcar (car/hkeys hash-key))))))
; => ["OK" "Sanfilippo" "Hickey"]

(wcar (car/ping)
      (car/with-parser clojure.string/lower-case (car/ping) (car/ping))
      (car/ping))
; => ["PONG" "pong" "pong" "PONG"]
