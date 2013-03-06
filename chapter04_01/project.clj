(defproject compojure-example "0.1.0"
  :description "Example Compojure project"
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [compojure "1.1.5"]
                 [hiccup "1.0.2"]
                 ]
  :plugins [[lein-ring "0.8.3"]]
  :ring {:handler compojure.example.routes/app})