(use 
	'[clojure.test] 
	'[clamq.activemq])
(require
   '[clamq.protocol.connection :as connection]
   '[clamq.protocol.consumer :as consumer]
   '[clamq.protocol.producer :as producer])

(defn producer-consumer-test [connection]
  (let [received (atom "")
        queue "producer-consumer-test-queue"
        consumer (connection/consumer connection {:endpoint queue :on-message #(reset! received %1) :transacted false})
        producer (connection/producer connection)
        test-message "producer-consumer-test"]
    (producer/publish producer queue test-message)
    (consumer/start consumer)
    (Thread/sleep 1000)
    (consumer/close consumer)
    (is (= test-message @received))))


(producer-consumer-test (activemq-connection "tcp://localhost:61616"))