(require '[clj-dbcp.core     :as dbcp]
         '[clojure.java.jdbc :as sql])

; create a datasource to the h2 database.
(def db-h2
  {:datasource
   (dbcp/make-datasource
     {:adapter :h2 :target :memory :database "nico"})})

(defn crud
  []
  (let [table :emp
        orig-record {:id 1 :name "Nico" :age 30}
        updt-record {:id 1 :name "Nico" :age 40}
        drop-table  #(sql/do-commands "DROP TABLE hito")
        retrieve-fn #(sql/with-query-results rows
                      ["SELECT * FROM hito WHERE id=?" 1]
                      (first rows))]
    (sql/with-connection db-h2
      ;; drop table if pre-exists
      (try (drop-table)
        (catch Exception _)) ; ignore exception
      ;; create table
      (sql/do-commands
        "CREATE TABLE hito (id INTEGER, name VARCHAR(50), age INTEGER)")
      ;; insert
      (sql/insert-values table (keys orig-record) (vals orig-record))
      ;; retrieve
      (println (retrieve-fn))
      ;; update
      (sql/update-values table ["id=?" 1] updt-record)
      ;; drop table
      (drop-table))))