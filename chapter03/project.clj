(defproject chapter03 "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [
  	; [org.clojure/clojure "1.4.0"]
    [org.clojure/clojure "1.5.0-alpha3"]

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
  ])
