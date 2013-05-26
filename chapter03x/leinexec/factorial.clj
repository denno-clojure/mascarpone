#!/bin/bash lein-exec

(defn fact [n] (reduce *' (range 1 (inc n))))

(def i (read-string (second *command-line-args*)))

(prn (fact i))