(use 'avout.core)
(use 'avout.locks)

(def client (connect "127.0.0.1"))

(def lock (distributed-lock client :lock-node "/lock"))

(if-lock lock
  (do 
  	(println "Have the lock")
  	(Thread/sleep 10000)
  	(println "Release lock")
  	)
  (println "Could not get the lock"))