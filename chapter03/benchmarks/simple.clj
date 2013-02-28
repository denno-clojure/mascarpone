(ns simple
  (:use perforate.core))

(defgoal simple-bench "A simple benchmark.")

(defcase simple-bench :really-simple
  [] (+ 1 1))

(defcase simple-bench :slightly-less-simple
  [] (+ 1 1 1))