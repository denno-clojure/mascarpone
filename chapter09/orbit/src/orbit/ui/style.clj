(ns orbit.ui.style
  (:require [clojure.string :as cstr]))

(defn bla [border]
  (let [s (cstr/split #" " border)]
    (case (count s)
      )
    ))

(defn border-style [style]
  (let [{:keys [border-width
                border-color
                border]} style
        sborder (cstr/split #" " border)
        width (or border-width
                  (first sborder))
        color (or border-color
                  (last sborder))]
    [:border ]))

(defn style->effect [style])
