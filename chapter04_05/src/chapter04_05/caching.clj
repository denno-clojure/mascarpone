(ns chapter04_05.caching
  (:require [immutant.cache :as cache])
  (:use 
	 ring.middleware.json
     ring.util.response))

(def c (cache/cache "bob" :ttl 5, :idle 1, :units :seconds))

(defn c-handler [request]
  (if (contains? c :a)
  	(response {:cached true :value (get c :a)})
    (do 
      (cache/put c :a true)
      (response {:cached false}))))

(def cached
  (wrap-json-response c-handler))