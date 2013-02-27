(require
    '[clj-dbcp.core        :as cp]
    '[clj-liquibase.change :as ch]
    '[clj-liquibase.cli    :as cli])
(use
    '[clj-liquibase.core :only (defchangelog)])

;; define the changes, changesets and the changelog
(def ct-change1 (ch/create-table :sample-table1
                  [[:id     :int          :null false :pk true :autoinc true]
                   [:name   [:varchar 40] :null false]
                   [:gender [:char 1]     :null false]]))

; recommended: one change per changeset
(def changeset-1 ["id=1" "author=shantanu" [ct-change1]])

; you can add more changesets later to the changelog
(defchangelog app-changelog "fooapp" [changeset-1])

;; keep the DataSource handy
(def ds (cp/make-datasource :h2 {:target :memory :database "nico"}))

; cli to migrate the schema
(cli/entry "update" {:datasource ds :changelog app-changelog})

; and to rollback 1 change
(cli/entry "rollback" {:datasource ds :changelog app-changelog :chs-count "1"})