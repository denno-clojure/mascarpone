(ns your-namespace
  (:require [netz.core :as netz]))

(def examples [[[0 0] [1]]
               [[0 1] [0]]
               [[1 0] [0]]
               [[1 1] [1]]])

; takes some time to train the network
(def network (netz/train examples {:hidden-neurons [1]}))

(netz/run network [0 0]) ; => [0.9176]
(netz/run network [0 1]) ; => [0.0549]
(netz/run network [1 0]) ; => [0.0728]
(netz/run network [1 1]) ; => [0.9307]