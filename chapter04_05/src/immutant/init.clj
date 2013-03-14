(ns immutant.init
  (:use chapter04_05.core)
  (:require [immutant.messaging :as messaging]
           [immutant.web :as web]
           [immutant.util :as util]))

(web/start "/" app)

;; Messaging allows for starting (and stopping) destinations (queues & topics)
;; and listening for messages on a destination.

; (messaging/start "/queue/a-queue")
; (messaging/listen "/queue/a-queue" #(println "received: " %))
