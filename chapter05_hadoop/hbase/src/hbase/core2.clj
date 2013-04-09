(ns hbase.core2)

(use '[clojure-hbase.admin :exclude [flush]])
(require '[clojure-hbase.core :as hb])
(import '[org.apache.hadoop.hbase.util Bytes])

(def test-tbl-name "test-users")

(hb/with-table [test-tbl (hb/table test-tbl-name)]
	(hb/as-map (hb/get test-tbl "testrow")
		:map-family    #(Bytes/toString %)
		:map-qualifier #(Bytes/toString %)
		:map-value     #(Bytes/toString %)))
; 