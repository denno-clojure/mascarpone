(ns hbase.setup)

(use '[clojure-hbase.admin :exclude [flush]])

(def test-tbl-name "test-users")
(def cf-name "test-cf-name")

(defn setup-tbl [] (create-table (table-descriptor test-tbl-name)))
(defn remove-tbl []
  (disable-table test-tbl-name)
  (delete-table test-tbl-name))

(setup-tbl)
(disable-table test-tbl-name)
(add-column-family test-tbl-name (column-descriptor cf-name))
; (add-column-family test-tbl-name (column-descriptor "test-cf-name1"))
; (add-column-family test-tbl-name (column-descriptor "test-cf-name2"))
(enable-table test-tbl-name)

; (remove-tbl)