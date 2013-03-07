(require 	'[lobos.connectivity :as c]
      		'[lobos.core :as l])
(use 		'lobos.schema)
(use 		'lobos.migration)

(def h2
  {:classname   "org.h2.Driver"
   :subprotocol "h2"
   :subname     "./korma"})

; open a connection globally
(c/open-global h2)

(defmigration add-users-table
  (up [] (l/create
          (table :users2
            (varchar :name 100 :unique)
            (check :name (> (length :name) 1)))))
  (down [] (l/drop (table :users2))))

(l/migrate)

(l/rollback)

(c/close-global h2)