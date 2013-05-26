(ns orbit.ui.effect
  (:require (orbit [configure :as c]
                    [util :as util]))
  (:import (de.lessvoid.nifty.builder
             EffectBuilder
             HoverEffectBuilder)))

(extend-type EffectBuilder
  c/Configurable
  (configure [this params]
    (c/configure-helper
      params param
      :length (.length this param)
      :once? (.oneShot this param)
      :parameters
      (doseq [[k v] param]
        (.effectParameter
          this (util/dash-to-camel k)
               v)))))

(defn ^EffectBuilder effect
  [effect & {:as options}]
  (let [effect (util/dash-to-camel (name effect))]
    (c/conf-int (EffectBuilder. effect) options)))

(defn ^HoverEffectBuilder hover-effect
  [effect & {:as options}]
  (let [effect (util/dash-to-camel effect)]
    (c/conf-int (HoverEffectBuilder. effect) options)))

(defn into-effect
  [obj]
  (cond
    (instance? EffectBuilder obj) obj
    (instance? HoverEffectBuilder obj) obj
    (vector? obj) (apply effect obj)
    :else (util/convert-err obj)))

(defn into-hover-effect
  [obj]
  (cond
    (instance? HoverEffectBuilder obj) obj
    (vector? obj) (apply hover-effect obj)
    :else (util/convert-err obj)))
