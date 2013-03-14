(defproject chapter04_08 "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :plugins [[lein-ring "0.8.3"]]
  :ring {:handler chapter04-08.core/app}
  :dependencies [
    [compojure "1.0.2"]
	  [ring/ring-jetty-adapter "1.1.0"]
    [liberator "0.8.0"]
  	[org.clojure/clojure "1.4.0"]])
