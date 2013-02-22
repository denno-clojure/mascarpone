(require '[taoensso.carmine.message-queue :as carmine-mq]) ; Add to `ns` macro

(def pool         (car/make-conn-pool))
(def spec-server1 (car/make-conn-spec))

(defmacro wcar [& body] `(car/with-conn pool spec-server1 ~@body))

(def myqueue "my-queue")

(def my-worker
  (carmine-mq/make-dequeue-worker
   pool spec-server1 myqueue
   :handler-fn (fn [msg] (println "Received" msg))))

(wcar 
 (carmine-mq/enqueue myqueue "my message!"))

; and the below will be showing up
; %> Received my message! 

(carmine-mq/stop my-worker)