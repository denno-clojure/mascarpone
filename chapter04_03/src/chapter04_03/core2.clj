(ns chapter04_03.core2
(:use 
	 ring.middleware.json
     ring.util.response))

(defn handler [request]
  ; (prn (get-in request [:body "user"]))
  (prn ((request :body) "user"))
  (response "Uploaded user."))

(def app
  (wrap-json-body handler))