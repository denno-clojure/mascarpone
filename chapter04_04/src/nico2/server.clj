(ns nico2.server
  (:require [noir.server :as server]))

(server/load-views-ns 'nico2.views)

(def handler (server/gen-handler {:mode :dev
                                  :ns 'nico2 }))

