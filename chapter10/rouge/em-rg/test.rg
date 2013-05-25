(ns test
  (:require [em-rg :as em])
  (:use rouge.core))

(defn now [] (.now ruby/Time))

(em/with
  (puts (now) " Execution begins.")
  (em/add-timer 1
    (puts (now) " First timer."))
  (em/add-timer 2
    (puts (now) " Bye bye.")
    (em/stop)))
