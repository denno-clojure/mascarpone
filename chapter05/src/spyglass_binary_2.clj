(ns spyglass_text
 (:require [clojurewerkz.spyglass.client :as c]))
 
(def tmc (c/bin-connection "127.0.0.1:11211 127.0.0.1:11212"))

(println (c/get tmc "a-key"))