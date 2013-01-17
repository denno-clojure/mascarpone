(require '[me.shenfeng.http.client :as http])

; get
(println (http/get "http://www.yahoo.co.jp"))

; post
(let [form-parms {:name "http-kit" :features ["async" "client" "server"]}
      {:keys [status headers body] :as resp} (http/post "http://host.com/path1"
                                                        {:form-parmas form-parms})]
  (if status
    (println "Async HTTP POST: " status)
    (println "Failed, exception is " resp)))