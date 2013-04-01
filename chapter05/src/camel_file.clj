(require '[clj-camel.core :as c])
(import '[org.apache.camel.impl SimpleRegistry DefaultCamelContext])
(import 'org.apache.activemq.camel.component.ActiveMQComponent)

(def test-routes
[
  [[:from "file://in"]
   ; [:to "log:com.mycompany.order?level=INFO"]
   [:to "file://out"]]
])

(defn start-camel-context []
  (let [r (SimpleRegistry.)
        ctx (doto (DefaultCamelContext. r))]
    (c/add-routes ctx test-routes)
    (.start ctx)
    ctx))

; start Camel
(def camel-context (start-camel-context))

; send a direct message
(def producer (.createProducerTemplate camel-context))
(.sendBody producer "file://in" "hello")

; once finished
; (.stop camel-context)