(ns orbit.jme.physics
  (:use [orbit.configure :only [Configurable
                                 conf-int configure-helper]])
  (:import (com.jme3.bullet
             BulletAppState
             BulletAppState$ThreadingType)))

(extend-type BulletAppState
  Configurable
  (configure [s params]
    (configure-helper
      params param
      :threading
      (case param
        :sequential
        BulletAppState$ThreadingType/SEQUENTIAL
        :parallel
        BulletAppState$ThreadingType/PARALLEL))))

(defn bullet-app-state []
  (let [b (BulletAppState.)]
    b))
