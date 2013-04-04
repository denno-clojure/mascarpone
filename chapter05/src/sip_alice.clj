(use 'cljain.dum)
(require '[cljain.sip.core :as sip]
  '[cljain.sip.address :as addr]
  '[cljain.sip.header :as header])

(defmethod handle-request :MESSAGE [request & [transaction]]
  (println "Received: " (String. (.getContent request)))
  (send-response! 200 :in transaction :pack "I am Alice and I received a message."))

(global-set-account :user "alice" :domain "localhost" :display-name "Alice" :password "123456")

(sip/global-bind-sip-provider! (sip/sip-provider! "my-app" "localhost" 5061 "udp"))
(sip/set-listener! (dum-listener))
(sip/start!)

(defn send-message[target msg]
  (send-request! :MESSAGE :to (addr/address target) :pack msg
    :on-success (fn [& {:keys [response]}] (println "Fine! response: " (.getContent response)))
    :on-failure (fn [& {:keys [response]}] (println "Oops!" (.getStatusCode response)))
    :on-timeout (fn [_] (println "Timeout, try it later.")))
  )

(send-message "sip:bob@localhost" "Hello, Bob.")