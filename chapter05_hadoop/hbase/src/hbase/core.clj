(ns hbase.core)

(use '[clojure-hbase.admin :exclude [flush]])
(require '[clojure-hbase.core :as hb])
(import '[org.apache.hadoop.hbase.util Bytes])

(def test-tbl-name "test-users")

(defn keywordize 
  [x] 
  (keyword (Bytes/toString x)))

(defn vectorize
  [result]
  (hb/as-vector result :map-family keywordize :map-qualifier keywordize :map-timestamp (fn [x] nil) :map-value keywordize))

; simple insert, one value
(hb/with-table [users (hb/table test-tbl-name)]
 (hb/put users "testrow" :value [:test-cf-name "test" "test"])
 (vectorize (hb/get users "testrow" :columns [:test-cf-name])))

; simple insert, multiple values
(hb/with-table [users (hb/table test-tbl-name)]
 (hb/put users "testrow1" :values [:test-cf-name [:test "test" :test2 "test2" :test3 "test3"]])
 (vectorize (hb/get users "testrow1" :columns [:test-cf-name])))

; simple delete
(hb/with-table [users (hb/table test-tbl-name)]
 (hb/delete users "testrow" :columns [:test-cf-name [:test :test2]]))
; nil

(hb/with-table [users (hb/table test-tbl-name)]
 (vectorize (hb/get users "testrow" :columns [:test-cf-name [:test :test2]])))
; []

(hb/with-table [users (hb/table test-tbl-name)]
    (hb/modify users 
      (hb/put* "testrow" :value [:test-cf-name :test "updated"])))
; [[:test-cf-name :test nil :updated]]