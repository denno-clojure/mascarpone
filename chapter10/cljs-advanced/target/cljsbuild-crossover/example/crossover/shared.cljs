; DO NOT EDIT THIS FILE! IT WAS AUTOMATICALLY GENERATED BY
; lein-cljsbuild FROM THE FOLLOWING SOURCE FILE:
; file:/Users/niko/projects/mascarpone/chapter10/cljs-advanced/src-clj/example/crossover/shared.clj

(ns example.crossover.shared
  (:require-macros
    [example.crossover.macros :as macros]))

(defn make-example-text []
  (macros/reverse-eval
    ("code" "shared " "from the " "おはよう " str)))
