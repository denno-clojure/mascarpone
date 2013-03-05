(ns chapter04.core)

(defn handler [request]
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body (str "Hello World. It is now " (java.util.Date.))})