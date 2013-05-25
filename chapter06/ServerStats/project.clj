(defproject server-stats "0.1"
  :description "Framework for ssh-based server stats and monitoring"
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [org.apache.commons/commons-email "1.2"]
                 [parallel-ssh "0.1"]
                 [org.clojure/tools.cli "0.2.1"]
                 [org.clojars.ghoseb/twilio-java "3.0.0-fix2"]]
  :main server-stats.core)
