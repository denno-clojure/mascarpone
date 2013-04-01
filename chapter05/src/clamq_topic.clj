(use 
    '[clojure.test] 
    '[clamq.activemq])
(require
   '[clamq.protocol.connection :as connection]
   '[clamq.protocol.consumer :as consumer]
   '[clamq.protocol.producer :as producer])

(defn producer-consumer-topic-test [connection]
  (let [received (atom "")
        topic "producer-consumer-topic-test-topic"
        consumer (connection/consumer connection {:endpoint topic :on-message #(reset! received %1) :transacted false :pubSub true})
        producer (connection/producer connection {:pubSub true})
        test-message "producer-consumer-topic-test"]
    (consumer/start consumer)
    (Thread/sleep 1000)
    (producer/publish producer topic test-message)
    (Thread/sleep 1000)
    (consumer/close consumer)
    (is (= test-message @received))))


(producer-consumer-topic-test (activemq-connection "tcp://localhost:61616"))