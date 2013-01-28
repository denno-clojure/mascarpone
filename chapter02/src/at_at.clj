(use 'overtone.at-at)

; everything is scheduled via a pool, so we need to create it beforehand
(def my-pool (mk-pool))

; do something 1000ms from now
(at (+ 1000 (now)) #(println "hello from the past!") my-pool)

; execute something 1000ms from now
(after 1000 #(println "hello from the past!") my-pool)

; repeat every 1s
(every 1000 #(println "I am cool!") my-pool)

; stop the last scheduled item
(stop *1)

; show what's in the pool
(show-schedule my-pool)

; stop and reset the pool to a clean state
(stop-and-reset-pool! my-pool)