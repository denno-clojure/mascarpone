(defproject vision-samples "1.0.0-SNAPSHOT"
  :description "OpenCV wrapper for Clojure."
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [org.clojure/core.incubator "0.1.0"]
				 [vision "1.0.0-SNAPSHOT"]
                 [org.clojars.nakkaya/jna "3.2.7"]]
  :jvm-opts ["-Djna.library.path=resources/lib/"])