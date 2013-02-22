(require '[monger.core :as m])
(require '[monger.collection :as c])
(import  '[org.bson.types ObjectId])

; connect and set the db
(m/connect!)
(m/set-db! (m/get-db "monger"))

;; with explicit document id (recommended)
(c/insert "documents" { :_id (ObjectId.) :first_name "John" :last_name "Lennon" })
 
;; multiple documents at once
(c/insert-batch "documents" [
	{ :first_name "John"  :last_name "Lennon" }
	{ :first_name "Ringo" :last_name "Starr"}
	{ :first_name "Paul"  :last_name "McCartney" }])
 
;; without document id (when you don't need to use it after storing the document)
(c/insert "document" { :first_name "John" :last_name "Lennon" })

;; returns the inserted document that includes generated _id
(c/insert-and-return "documents" {:name "John" :age 30})
(c/find "documents" {:first_name "Ringo"})

;; returns all documents as Clojure maps
(c/find-maps "documents")
 
;; returns documents with year field value of 1998, as Clojure maps
(c/find-maps "documents" { :name "John" })

; return only one object
(c/find-one "documents" { :name "John" })

; update or create a new document 
(c/update "documents" {:name "John"} {:age 32} :upsert true)

; remove documents
(c/remove "documents" { :name "Yoko" })