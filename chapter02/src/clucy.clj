 (require '[clucy.core :as clucy])

; create an in memory index
(def index (clucy/memory-index))

; to create a file based index
(clucy/disk-index "tmp")

; add some values to be stored
(clucy/add index
   {:name "Bob", :job "Builder"}
   {:name "Donald", :job "Computer Scientist"})

; delete some value
 ; (clucy/delete index
 ;   {:name "Bob", :job "Builder"})

(clucy/search index "bob" 10)
; ({:name "Bob", :job "Builder"})

(clucy/search index "scientist" 10)
; ({:name "Donald", :job "Computer Scientist"})

; compose queries with AND
(clucy/search index "name:bob AND job:Builder" 10)
; ({:name "Bob", :job "Builder"})