(defproject chapter03 "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :cucumber-feature-paths ["test/features/"]

  :profiles {:dev {:dependencies [
    [midje "1.5-RC1"]
  ]}}

  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [
  	; [org.clojure/clojure "1.4.0"]
    [org.clojure/clojure "1.5.0-beta1"]

  	; fractals
  	[net.mikera/clisk "0.7.0"]

  	; Math
  	[org.hellonico/gajure "1.0.1"]
  	[lspector/clojush "1.2.39"]
    [incanter/incanter-latex "1.3.0" :exclusions [org.danlarkin/clojure-json]]
    [org.clojure/core.logic "0.8.0-rc2"]
    [mimir/mimir "0.1.0"]

    ; NoSQL
    [com.novemberain/monger "1.4.2"]
    [org.clojure/data.json       "0.2.1"]
    [com.taoensso/carmine "1.5.0"]
    [com.novemberain/langohr "1.0.0-beta11"]
    [cupboard "1.0beta1"]
    [fleetdb-client "0.2.2"]

    ; SQL
    [com.h2database/h2 "1.3.154"]
    [hellonico/lobos "1.0.0-beta1"]
    [korma "0.3.0-beta7" :exclusions [org.clojure/clojure]]

    ; liquidbase
    [clj-dbcp      "0.8.0"]  ; to create connection-pooling DataSource
    [clj-liquibase "0.4.0"]  ; for this library
    [oss-jdbc      "0.8.0"]  ; for Open Source JDBC drivers

    ; Remote Libs
    [factql "1.0.3"]
    [hellonico/zenclient "1.2"]
    [clj-mixpanel "0.0.1-SNAPSHOT"]
    [clj-todoist "1.0.0"]

    ; testing
    ; [midje "1.4.0"]
    [clj-webdriver "0.6.0-beta2"]
    [criterium "0.3.1"]
    [speclj "2.5.0"]

    ; Ring testing
    [com.gfredericks/vcr-clj "0.2.2"]
    [ring/ring-jetty-adapter "1.1.2"] ; for testing above
    [kerodon "0.0.7"]
      [net.cgrand/moustache "1.2.0-alpha1"] ; to help the above
      [hiccup "1.0.2"]
    [ring-mock "0.1.3"]

    ; validation 
    [bouncer "0.2.3-beta1"]
  ])
