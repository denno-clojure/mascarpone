(ns screenscraping
  (:use net.cgrand.enlive-html))


; define a template
(deftemplate index "enlive.html"
  [ctxt]
  [:p#message] (content (:message ctxt)))

; use it with no message
(index {})

; use it with a message
(index {:message "Hello !"})