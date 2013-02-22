(import '[com.rabbitmq.client Connection Channel AMQP])

; all the different namespaces
(require '[langohr.core :as lhc]
	     '[langohr.consumers :as lhcons]
         '[langohr.queue :as lhq]
         '[langohr.exchange :as lhe]
         '[langohr.basic :as lhb]
         '[langohr.util :as lhu])

; this is taken from the langohr test suite
(let [
	channel (.createChannel (lhc/connect) )
    exchange ""
    payload ""
    queue "using-default-exchange"
    declare-ok (lhq/declare channel queue :auto-delete true)
    tag (lhu/generate-consumer-tag "langohr.basic/consume-tests")
    content-type "text/plain"
    msg-id (.toString (java.util.UUID/randomUUID))
    n 3
    latch (java.util.concurrent.CountDownLatch. n)
    msg-handler (fn [ch metadata payload]
                    (prn (:delivery-tag metadata))
                    (prn (:content-type metadata))
                    (prn (:headers metadata))
                    (prn (:message-id metadata))
                    (prn (:priority metadata))
                    (.countDown latch))]
    (.start (Thread. #(lhcons/subscribe channel queue msg-handler :consumer-tag tag :auto-ack true) "t-publishing-using-default-exchange-and-default-message-attributes/consumer"))
    (.start (Thread. (fn []
                       (dotimes [i n]
                         (lhb/publish channel exchange queue payload
                                      :priority 8
                                      :message-id msg-id
                                      :content-type content-type
                                      :headers { "see you soon" "à bientôt" }))) "publisher"))
    (.await latch))