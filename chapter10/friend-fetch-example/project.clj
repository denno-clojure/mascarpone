(defproject friendly "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [noir "1.3.0-beta8"]
                 [fetch "0.1.0-alpha2"]
                 [com.cemerick/friend "0.1.5"]
				]
  :plugins [[lein-cljsbuild "0.2.5"]]
  :cljsbuild {
              :builds [{
                        :source-path "src-cljs"
                        :compiler {
                                   :output-to "resources/public/js/main.js"
                                   :optimizations :whitespace
                                   :pretty-print true}}]}
  :main friendly.server)