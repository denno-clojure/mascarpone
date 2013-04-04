(ns spyglass_text
 (:require [clojurewerkz.spyglass.client :as c]))
 
(def tmc (c/bin-connection "127.0.0.1:11211 127.0.0.1:11212"))
(c/set tmc "a-key" 0 '[1 2 3 4 5])

(println (c/get tmc "a-key"))