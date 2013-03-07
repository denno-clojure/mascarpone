(defproject chapter04_03 "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :plugins [[lein-ring "0.8.3"]]
  :ring {:handler chapter04_03.core2/app}
  :dependencies [
  [org.clojure/clojure "1.5.0-RC3"]
  [ring/ring-json "0.2.0"]
  ])