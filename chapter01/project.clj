(defproject sample "0.1.0-SNAPSHOT"
  ; the description for your project
  :description "FIXME: write description"
  ; the url where this project is hosted
  :url "http://example.com/FIXME"
  ; any kind of license you want to specify
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  ; the namespace that will be loaded when lein repl starts
  :main sample.core
  ; some dependencies you do not want to see them everywhere
  :profiles {:dev {:dependencies [
      [com.cemerick/pomegranate "0.0.13"]]}}

  ; pure java settings
  :java-source-paths ["src/java"]
  :javac-opts ["-target" "1.6" "-source" "1.6" "-Xlint:-options"]
  :compile-path "target/classes"

  ; path for scala sources
  :scala-source-path "src/scala"
  ; force scala to run before doing anything
  :prep-tasks ["scalac" "javac"]
  ; declare all your dependencies here
  :dependencies [
    [org.clojure/clojure "1.4.0"]
    [org.scala-lang/scala-library "2.9.1"]
    [cheshire "5.0.1"]
    [commons-io/commons-io "2.4"]
  	])