(defproject chapter02 "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [
    ; clojure
    [org.clojure/clojure "1.5.0-alpha5"]

  	; colors in your REPL
  	[colorize "0.1.1"]

  	; http kit, websockets
  	[me.shenfeng/http-kit "2.0-SNAPSHOT"]

    ; statistics
    [incanter "1.3.0"]

    ; graph 
    [lacij "0.7.1"]

    ; UI
    [seesaw "1.4.2"]
    [com.github.insubstantial/substance "7.1"]

    ; xml parsing
    [org.clojars.kyleburton/clj-xpath "1.4.0"]

    ; html parsing
    [clojure-soup "0.0.1"]

    ; templating
    [enlive "1.0.1"]
    
    ; docjure
    [dk.ative/docjure "1.6.0"]

    ; emails
    [com.draines/postal "1.9.2"]

    ; dns
    [com.brweber2/clj-dns "0.0.2"]  

    ; clostache
    [de.ubercode.clostache/clostache "1.3.1"] 

    ; csv
    [clojure-csv "2.0.0-alpha2"]

    ; RSS
    [clj-rss "0.1.2"]

    ; growl
    [clj-growlnotify "0.1.1"]

    ; JSON
    [cheshire "5.0.1"]

    ; digest
    [digest "1.3.0"]
  ])