(defproject cloudbees "0.1.0-SNAPSHOT"
  :cloudbees-app-id "hellonico/sundayafternoon"
  
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [lib-noir "0.5.0"]
                 [compojure "1.1.5"]
                 [ring-server "0.2.7"]
                 [clabango "0.5"]
                 [com.taoensso/timbre "1.5.2"]
                 [com.taoensso/tower "1.5.1"]
                 [markdown-clj "0.9.19"]]
  :plugins [
  [lein-ring "0.8.3"]
  [lein-cloudbees "1.0.4"]
  ]
  :ring {:handler cloudbees.handler/war-handler
         :init    cloudbees.handler/init
         :destroy cloudbees.handler/destroy}
  :profiles
  {:production {:ring {:open-browser? false
                       :stacktraces?  false
                       :auto-reload?  false}}
   :dev {:dependencies [[ring-mock "0.1.3"]
                        [ring/ring-devel "1.1.8"]]}}
  :min-lein-version "2.0.0")