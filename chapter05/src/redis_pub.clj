(require '[labs.redis.core :as redis])

(def db (redis/client))

(dotimes [n 5] 
  @(redis/publish db "msgs" (str "hello " n)))