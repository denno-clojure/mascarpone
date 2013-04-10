(defproject hbase_query "0.1.0-SNAPSHOT"
	:description "FIXME: write description"
	:url "http://example.com/FIXME"
	:license {:name "Eclipse Public License" :url "http://www.eclipse.org/legal/epl-v10.html"}
	:dependencies [
		[org.clojure/clojure "1.5.1"]
  		[hbase.cascalog "0.2-SNAPSHOT"]
	]
	:profiles {:provided
		{:dependencies [
		[org.apache.hadoop/hadoop-core "0.20.2"]
		[org.slf4j/slf4j-log4j12 "1.6.1"]
		[org.apache.hbase/hbase "0.92.0" :exclusions [org.apache.thrift/libthrift org.slf4j/slf4j-api]]]}
	})