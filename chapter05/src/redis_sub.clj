(require '[labs.redis.core :as redis])

(def db (redis/client))

(redis/subscribe-with db
   ["msgs" "msgs2" "msgs3"]
   (fn [db channel message]
     (let [message (redis/->str message)]
       (println "R " channel message)
       (not (= "quit" message)))))