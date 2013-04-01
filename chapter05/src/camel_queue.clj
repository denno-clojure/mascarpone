(require '[clj-camel.core :as c])
(import '[org.apache.camel.impl SimpleRegistry DefaultCamelContext])
(import 'org.apache.activemq.camel.component.ActiveMQComponent)

(def test-routes
[
  [
  [:from "file://in-mq"]
  [:to "activemq:queue:FOO.BAR"]]
])

(def activemq-url "tcp://localhost:61616")

(defn start-camel-context []
  (let [r (SimpleRegistry.)
        ctx (doto (DefaultCamelContext. r))]
    (.addComponent ctx "activemq" (ActiveMQComponent/activeMQComponent activemq-url))
    (c/add-routes ctx test-routes)
    (.start ctx)
    ctx))

(def camel-context (start-camel-context))

; send a direct message
(def producer (.createProducerTemplate camel-context))
(.sendBody producer "file://in" "hello")

; once finished
; (.stop camel-context)