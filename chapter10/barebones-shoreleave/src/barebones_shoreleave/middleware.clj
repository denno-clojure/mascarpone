(ns barebones-shoreleave.middleware)

(defn wrap-ring-dump
  "Dumps out request/response for debugging."
  [handler & prefix]
  (fn [request]
    (let [response (handler request)]
      (do
        (if-let [prefix (first prefix)]
          (println prefix))
        (println "\n\nREQUEST: " request)
        (println "\n\nRESPONSE: " response)
        (println "\n------------------------------\n\n"))
      response)))

(defn wrap-add-anti-forgery-cookie
  "Mimics code in Shoreleave-baseline's
   customized ring-anti-forgery middleware."
  [handler & [opts]]
  (fn [request]
    (let [response (handler request)]
      (if-let [token (-> request :session (get "__anti-forgery-token"))]
        (assoc-in response [:cookies "__anti-forgery-token"] token)
        response))))
