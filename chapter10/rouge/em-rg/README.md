Sample usage:

    (ns my-test
      (:require [em-rg :as em])
      (:use ruby rouge.core))

    (em/with
      (puts "Execution begins: " (.now Time))
      (em/add-timer 1
        (puts "First timer: " (.now Time)))
      (em/add-timer 2
        (puts "Bye bye: " (.now Time))
        (em/stop)))
