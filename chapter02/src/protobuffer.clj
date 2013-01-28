(use 'flatland.protobuf.core)
(import Personclj$Person)

(def Person (protodef Personclj$Person))

(def p (protobuf Person :id 4 :name "Bob" :email "bob@example.com"))
;=> {:id 4, :name "Bob", :email "bob@example.com"}

(assoc p :name "Bill")
;=> {:id 4, :name "Bill", :email "bob@example.com"}

(assoc p :likes ["climbing" "running" "jumping"])
;=> {:id 4, name "Bob", :email "bob@example.com", :likes ["climbing" "running" "jumping"]}

(def b (protobuf-dump p))
;=> #<byte[] [B@7cbe41ec>

(protobuf-load Person b)
;=> {:id 4, :name "Bob", :email "bob@example.com"}