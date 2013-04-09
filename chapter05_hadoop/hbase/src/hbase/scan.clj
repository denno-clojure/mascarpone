(ns hbase.scan)

(use '[clojure-hbase.admin :exclude [flush]])
(require '[clojure-hbase.core :as hb])
(import '[org.apache.hadoop.hbase.util Bytes])

(def test-tbl-name "test-users")

; helpers
(defn keywordize 
  [x] 
  (keyword (Bytes/toString x)))
(defn mapize
  [result]
  (hb/latest-as-map result :map-family keywordize :map-qualifier keywordize
                 :map-value #(Bytes/toString %)))

; scanners
(hb/with-table [test-tbl (hb/table test-tbl-name)]
	(hb/with-scanner [scan-results (hb/scan test-tbl :family :test-cf-name)]
                (doall 
                	(map mapize
                            (-> scan-results .iterator iterator-seq)))))