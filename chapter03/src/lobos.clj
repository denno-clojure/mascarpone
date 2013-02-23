; create SQL tables with lobos
(require 	'[lobos.connectivity :as c]
      		'[lobos.core :as l]
      		'[lobos.analyzer :as a])
(use 		'[lobos.schema])
 
; connection details, driver and url
(def h2
  {:classname   "org.h2.Driver"
   :subprotocol "h2"
   :subname     "./korma"})

; open a connection globally
(c/open-global h2)

; create a table
(l/create h2
  (table :users
  	(integer :id :auto-inc :primary-key) ; primary key
    (varchar :first 100) 
    (varchar :last 100)))

; print the table we have just created
(pprint (-> (a/analyze-schema) :tables :users))

; delete the table
(l/drop (table :users))

; close the connection
(c/close-global h2)