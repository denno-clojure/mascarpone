(defproject palletquickstart "0.1.0-SNAPSHOT"
  :description "FIXME Pallet project for palletquickstart"
  :dependencies [
                 [vmfest "0.2.8"]
                 [org.cloudhoist/pallet-vmfest "0.3.0-alpha.3"]

                 [org.clojure/clojure "1.4.0"]
                 [org.cloudhoist/pallet "0.7.4"]
                 [org.cloudhoist/pallet-jclouds "1.5.2"]
                 ;; To get started we include all jclouds compute providers.
                 ;; You may wish to replace this with the specific jclouds
                 ;; providers you use, to reduce dependency sizes.
                 [org.jclouds/jclouds-allblobstore "1.5.5"]
                 [org.jclouds/jclouds-allcompute "1.5.5"]
                 [org.jclouds.driver/jclouds-slf4j "1.5.5"
                  ;; the declared version is old and can overrule the
                  ;; resolved version
                  :exclusions [org.slf4j/slf4j-api]]
                 [org.jclouds.driver/jclouds-sshj "1.5.5"]
                 [ch.qos.logback/logback-classic "1.0.9"]]
  :dev-dependencies [[org.cloudhoist/pallet
                      "0.7.4" :type "test-jar"]
                     [org.cloudhoist/pallet-lein "0.5.2"]]
  :profiles {:dev
             {:dependencies
              [[org.cloudhoist/pallet "0.7.4"
                :classifier "tests"]]
              :plugins
              [[org.cloudhoist/pallet-lein "0.5.2"]]}
             :leiningen/reply
             {:dependencies [[org.slf4j/jcl-over-slf4j "1.7.2"]]
              :exclusions [commons-logging]}}
  :local-repo-classpath true
  :repositories
  {"sonatype" "https://oss.sonatype.org/content/repositories/releases/"})
