(ns quilltest.scene
  (:require [quil.core :as q]))

;;; Scene graph helpers
(defprotocol Node
  "A functional scene graph node, which can draw itself
   and its children"
  (draw [this state]))

(defrecord GraphNode [children draw-fn]
  Node
  (draw [this state]
    (draw-fn state)
    (doseq [x children]
      (draw x state))))