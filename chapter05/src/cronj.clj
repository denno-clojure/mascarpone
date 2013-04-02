(require '[cronj.core :as cj])

(cj/defcronj cnj
  :entries [
  {:id       :t1
    :handler  (fn [dt opts] (println "I need red wine! " dt))
   :schedule "0-60/2 * * * * * *"
 }
 {:id       :t2
   :handler  (fn [dt opts] (println "I need champagne! " dt))
   :schedule "1-60/2 * * * * * *"
   }])

(cj/start! cnj)

; I need red wine!  #<DateTime 2013-04-02T10:42:24.000+09:00>
; I need champagne!  #<DateTime 2013-04-02T10:42:25.000+09:00>
; I need red wine!  #<DateTime 2013-04-02T10:42:26.000+09:00>
; I need champagne!  #<DateTime 2013-04-02T10:42:27.000+09:00>
; I need red wine!  #<DateTime 2013-04-02T10:42:28.000+09:00>
; I need champagne!  #<DateTime 2013-04-02T10:42:29.000+09:00>

    (Thread/sleep 5000)

;; stop shouting and stop
(cj/stop! cnj)