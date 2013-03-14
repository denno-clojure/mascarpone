(ns chapter04_05.core
  (:use 
	 ring.middleware.json
     ring.util.response))

(defn handler [request]
  (response {:foo "bar"}))

(def app
  (wrap-json-response handler))