(ns leiningen.stock
	; to parse xml
	(:use clojure.xml)
	; to stream the web content 
	(:use clojure.java.io))

; base service url
(def base "http://www.google.com/ig/api?stock=")

; method to retrieve the quote from the web
(defn get-quote [key]
   (-> 
   	 (get (-> (parse (input-stream (str base key))) :content first :content) 10) 
   	 :attrs 
   	 :data))

; method called from leiningen
(defn stock [project & args] 
	(println (get-quote (first args))))