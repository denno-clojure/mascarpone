(use 'me.shenfeng.http.server)

(def clients (atom {}))
(defn chat-handler [req]
  (when-ws-request req con
                   (println con "connected")
                   (swap! clients assoc con 1)
                   (on-mesg con (fn [msg]
                                (send-mesg con msg)))))

(run-server chat-handler {:port 8080})