(use '[timely.core])

; debugging methods
(defn test-print-schedule
  [test_id]
  (println (str (java.util.Date.) ": Item scheduled - " test_id)))
(defn test-print-fn
  [test_id]
  (partial test-print-schedule test_id))

; start scheduler
(start-scheduler)

; create a schedule to repeat every minute
(def item (scheduled-item
            (each-minute)
            (test-print-fn "Scheduled using start-schedule")))
; start the item
(def sched-id (start-schedule item))
; sleep for a minute
(Thread/sleep (* 1000 60 2))
; stop the item
(end-schedule sched-id)