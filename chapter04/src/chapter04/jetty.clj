(ns chapter04.jetty
    (:use chapter04.core)
    (:use ring.adapter.jetty))

(run-jetty handler {:port 8080})