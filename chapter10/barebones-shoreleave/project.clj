(defproject barebones-shoreleave "0.1.0-SNAPSHOT"
  :description "Bare-bones Shoreleave"
  :url "https://github.com/ddellacosta/barebones-shoreleave"

  :dependencies [[org.clojure/clojure "1.5.1"]
                 [compojure "1.1.5" :exclusions [ring/ring-core]]
                 [ring "1.1.8"]
                 [shoreleave/shoreleave-remote "0.3.0"]
                 [shoreleave/shoreleave-remote-ring "0.3.0"]
                 [ring-anti-forgery "0.2.1"]
                 [enlive "1.1.1"]
                 [jayq "2.3.0"]]

  :plugins [[lein-ring "0.8.3"]
            [lein-cljsbuild "0.3.0"]]

  :ring {:handler barebones-shoreleave.handler/app}

  :cljsbuild {:builds
              [{:source-paths ["src"],
                :compiler {:output-dir "resources/build/cljs",
                           :output-to "resources/public/js/baseline.js",
                           :optimizations :simple,
                           :pretty-print true}}]}

  :profiles {:dev
             {:dependencies [[ring-mock "0.1.3"]]}})
