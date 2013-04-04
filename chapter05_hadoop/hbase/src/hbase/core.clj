(ns hbase.core)

(require ['clojure-hbase.core :as 'hb])

  (hb/with-table [users (hb/table "test-users")]
     (hb/put users "testrow" :values [:account [:c1 "test" :c2 "test2"]])
     (hb/get users "testrow" :columns [:account [:c1 :c2]])
     (hb/delete users "testrow" :columns [:account [:c1 :c2]])
     (hb/get users "testrow" :columns [:account [:c1 :c2]]))
  

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