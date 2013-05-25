(ns test
  (:require mechanize)
  (:use rouge.core ruby))

(def agent (Mechanize.))
(def page (.get agent "http://github.com"))

(.each (.links page) | [link]
  (puts (.text link)))

(def page (-> (.links page)
              (.find | [l] (= (.text l) "About us"))
              (.click)))

(puts (.inspect page))