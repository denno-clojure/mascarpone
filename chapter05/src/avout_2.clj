(use 'avout.core)
(def client (connect "127.0.0.1"))

(def r0 (zk-ref client "/r0"))

; should be set by the other avout
(println @r0) 