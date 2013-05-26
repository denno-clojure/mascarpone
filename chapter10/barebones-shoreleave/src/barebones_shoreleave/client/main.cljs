(ns barebones-shoreleave.main
  (:use
   [jayq.core :only [$ bind]])
  (:require
   [shoreleave.remote])
  (:require-macros
   ;; https://github.com/shoreleave/shoreleave-remote
   [shoreleave.remotes.macros :as srm]))

(def $click ($ :a#click))

(bind
 $click "click"
 (fn []
   (srm/rpc
    (ping "Testing...") [pong-response]
    (js/alert pong-response))))
