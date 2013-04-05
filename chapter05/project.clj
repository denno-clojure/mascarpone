(defproject chapter05 "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
  :url "http://www.eclipse.org/legal/epl-v10.html"}
  :resource-paths ["resources/jfxrt.jar"]
  :dependencies [
  [org.clojure/clojure "1.5.1"]

  [clamq/clamq-jms "0.4"]
  [clamq/clamq-activemq "0.4"]

  [clj-camel "1.0.1"]
  [org.slf4j/slf4j-simple "1.7.5"]  

  [clojurewerkz/quartzite "1.0.1"]
  ; [com.novemberain/quartz-mongodb "1.1.0"]

  [cronj "0.6.1"]

  [hellonico/labs.redis "0.1.1"]
  [com.taoensso/carmine "1.6.0"]

  [hellonico/avout "0.5.4"]

  [clojurewerkz/spyglass "1.1.0-beta3"]

  [cljain "0.4.0"]

  [org.clojars.paul/clj-hector "0.2.8"]

  ])