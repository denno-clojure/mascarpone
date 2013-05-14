(ns quilltest.keys
  (:require [clojure.set :as set]))

;;; Functions for generating keymaps

;;; known keys, add used ones here
(def key->int
  {:w 87
   :a 65
   :s 83
   :d 68
   :q 81})

(def int->key
  (set/map-invert key->int))

(defn int-key-code [^java.awt.event.KeyEvent event]
;  (println event)
  (.getKeyCode event))

(defn gen-on-keypress
  "Creates a function that updates an atom of currently pressed
   keys, the value of which will be a set of keywords."
  [keys-atom]
  (fn [event]
    (when-let [key (int->key (int-key-code event))]
      (swap! keys-atom #(if (nil? %) #{key} (conj % key))))))

(defn gen-on-keyrelease
  "Creates a function that updates an atom of currently pressed
   keys when one is released"
  [keys-atom]
  (fn [event]
    (when-let [key (int->key (int-key-code event))]
      (swap!
       keys-atom
       #(if-let [new (disj % key)] new #{})))))