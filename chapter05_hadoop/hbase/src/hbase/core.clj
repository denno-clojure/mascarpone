(ns hbase.core)

(use '[clojure-hbase.admin :exclude [flush]])
(require '[clojure-hbase.core :as hb])
(import '[org.apache.hadoop.hbase.util Bytes])

(def test-tbl-name "test-users")

; (hb/with-table [users (hb/table test-tbl-name)]
; (hb/put users "row" :value [:test-cf-name1 :testqual :testval]))

; (hb/put users "testrow" :values [:test-cf-name1 [:test1qual1 "testval1"
;                                                   :test1qual2 "testval2"]
;                                   :test-cf-name2 [:test2qual1 "testval3"
;                                                   :test2qual2 "testval4"]]))

(defn keywordize [x] (keyword (Bytes/toString x)))

(defn test-vector
  [result]
  (hb/as-vector result :map-family keywordize :map-qualifier keywordize :map-timestamp (fn [x] nil) :map-value keywordize))

  (hb/with-table [users (hb/table test-tbl-name)]
     (hb/put users "testrow" :value [:test-cf-name "test" "test"])

     (test-vector (hb/get users "testrow" :columns [:test-cf-name])))
     ; (hb/delete users "testrow" :columns [:account [:c1 :c2]])
     ; (hb/get users "testrow" :columns [:account [:c1 :c2]]))
  
; (remove-tbl)


  ; (hb/with-table [users (hb/table "test-users")]
  ;        (hb/put users "testrow" :values [:account [:c1 "test" :c2 "test2"]]))
  ; ; nil

  ; (hb/with-table [users (hb/table "test-users")]
  ;        (hb/get users "testrow" :columns [:account [:c1 :c2]]))
  ; ; #<Result keyvalues={testrow/account:c1/1265871284243/Put/vlen=4, testrow/account:c2/1265871284243/Put/vlen=5}>

  ; (hb/with-table [users (hb/table "test-users")]
  ;        (hb/delete users "testrow" :columns [:account [:c1 :c2]]))
  ; ; nil

  ; (hb/with-table [users (hb/table "test-users")]
  ;        (hb/get users "testrow" :columns [:account [:c1 :c2]]))
  ; ; #<Result keyvalues=NONE>