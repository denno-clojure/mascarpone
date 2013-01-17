(use 'me.shenfeng.http.server)


(defn app [req]
  {:status  200
   :headers {"Content-Type" "text/html"}
   :body    "hello word"})

; (run-server app {:port 8080
;                  :thread 4                     ; 4 http worker thread
;                  :queue-size 20480             ; max job queued before reject to project self
;                  :ip "127.0.0.1"               ; bind to localhost
;                  :worker-name-prefix "worker-" ; thread name worker-1, worker-2, worker-3, ......
;                  :max-line 4096                ; max http header line length
;                  :max-body 1048576})           ; max http request body, 1M

(run-server app {}) ; runs on port 8090 by default