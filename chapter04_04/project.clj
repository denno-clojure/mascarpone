(defproject nico2 "0.1.0-SNAPSHOT"
            :description "FIXME: write this!"
            :plugins [[lein-ring "0.8.3"]]
            :dependencies [[org.clojure/clojure "1.5.0-RC3"]
                           [noir "1.3.0-beta3"]]
			:ring {:handler nico2.server/handler}
            :main nico2.server)