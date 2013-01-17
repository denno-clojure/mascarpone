(ns hello-seesaw.core
  (:use seesaw.core))

  (invoke-later
    (-> (frame :title "Hello",
           :content "Hello, Seesaw",
           :on-close :exit)
     pack!
     show!))