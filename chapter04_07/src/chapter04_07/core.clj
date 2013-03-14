(ns chapter04-07.core
	(:use compojure.core)
	(:use org.httpkit.server))

(defn chat-handler [req]
  (when-ws-request req ws-con  
     (on-mesg ws-con (fn [msg]
                         (send-mesg ws-con (str msg " ! "))))
     (on-close ws-con (fn [status] (println ws-con "closed")))))

(defroutes web-handler
  (GET "/chat" [] chat-handler))

(defn -main[& args]
	(run-server web-handler {:port 8080}))