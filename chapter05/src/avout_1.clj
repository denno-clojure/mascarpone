(use 'avout.core)
(def client (connect "127.0.0.1"))

(def r0 (zk-ref client "/r0" 0))

(dosync!! client
  (alter!! r0 inc))