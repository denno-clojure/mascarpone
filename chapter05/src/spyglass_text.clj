(ns spyglass_text
 (:require [clojurewerkz.spyglass.client :as c]))
 
(def tmc (c/text-connection "127.0.0.1:11211"))

;; set a key that expires in 3 seconds
(def res (c/set tmc "a-key" 3 "a value"))

;; results of async operations in Spyglass can be @dereferenced
(println @res)

(println (c/get tmc "a-key"))
; wait 3 seconds
(Thread/sleep 3000)
(println (c/get tmc "a-key"))

(c/shutdown tmc)