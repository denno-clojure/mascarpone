(defproject ops "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :profiles {:dev
                {:dependencies [
				[clj-ssh-tunnel "0.1.1"]
				]
        }}
  :dependencies [
	[org.clojure/clojure "1.5.1"]
	[org.clojure/core.incubator "0.1.2"]
	])