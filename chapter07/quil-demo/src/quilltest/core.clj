(ns quilltest.core
  (:use quil.core)
  (:require [quil.applet :as applet]))

(def dobreak true)
(def dobreak false)

(defn nanotime []
  (System/nanoTime))

(defn game-loop [update-fps loop-fn exit?-fn]
  (let [mutex-refresh (java.util.concurrent.Semaphore. 0)
        mutex-refreshing (java.util.concurrent.Semaphore. 1)
        timer (java.util.Timer.)
        task (proxy [java.util.TimerTask] []
               (run []
                 (when (.tryAcquire mutex-refreshing)
                   (do (.release mutex-refreshing)
                       (.release mutex-refresh)))))]
    (.scheduleAtFixedRate timer task (long 0) (long (/ 1000 update-fps)))
    (loop [old-time (nanotime)]
      (let [new-time (do (.acquire mutex-refresh)
                         (.acquire mutex-refreshing)
                         (nanotime))
            ticks (long (- new-time old-time))]
        (loop-fn ticks)
        (.release mutex-refreshing)
        (when-not (exit?-fn)
          (recur new-time))))))

(defmacro with-applet [name & body]
  `(binding [quil.dynamics/*applet* ~name]
    (do ~@body)))

(defn run-sketch [sketch-params key-fns update-fps loop-fn exit?-fn]
  (let [{:keys [title setup draw size]} sketch-params
        {:keys [on-key-press on-key-release]} key-fns
        mysketch (applet/applet
                :title title
                :setup setup
                :draw draw
                :size size)]
    (Thread/sleep 5)
    (.addKeyListener
     mysketch
     (reify java.awt.event.KeyListener
       (keyPressed [this event] (on-key-press event))
       (keyReleased [this event] (on-key-release event))
       (keyTyped [this event] nil)))
    (with-applet mysketch
      (game-loop update-fps loop-fn exit?-fn))))










