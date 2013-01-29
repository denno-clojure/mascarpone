(use 'clj-esper.core)
(use '[clojure.walk :only (stringify-keys)])

; define an event
(defevent FooEvent [foo :string])

; define a query
(defstatement select-test "SELECT foo FROM FooEvent")

; define a callback when an event arrives
(defn handler
  [msg]
  (println "Message: " (.get msg "foo")))

; examine a new event
(def evt-1 (new-event FooEvent :foo "Hello, World!"))

; create a ref to keep the service
(def s (ref nil))

; create the esper service with uri and event types
(with-esper service {:uri "/events" :events #{FooEvent}}
 (dosync (ref-set s service))
 (attach-statement select-test handler)) ; attach callbacks depending on the query

; function to insert a new event on the local service queue
(defn call-event[event] 
	(send-event @s (stringify-keys event) ((meta event) :name)))

; insert a new event
(call-event evt-1)