(ns example.opengl.text
  (:use [penumbra opengl])
  (:require [penumbra [app :as app] [text :as text]]))

(defn display [_ _]
  (text/with-font (text/font "Inconsolata" :size 50)
    (text/write-to-screen "hello world" 0 0)
    (text/write-to-screen "hello world" 0 100)))

(defn start []
  (app/start {:display display} {}))
