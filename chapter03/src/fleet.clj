(use 'fleetdb.client)
(def client (connect {:host "127.0.0.1", :port 3400}))

(client ["ping"])
; => "pong"

; insert some data
(client ["insert" "accounts" {"id" 1, "owner" "Eve", "credits" 100}])
; => 1

; multiple records
(client ["insert" "accounts"
          [{"id" 2, "owner" "Bob", "credits" 150}
           {"id" 3, "owner" "Dan", "credits" 50}
           {"id" 4, "owner" "Amy", "credits" 1000, "vip" true}]])
; => 3

; to retrieve records
(client ["select" "accounts" {"where" ["=" "id" 2]}])
; => [{"id" 2, "owner" "Bob", "credits" 150}]

; select records by id
(client ["select" "accounts" {"where" ["in" "id" [1 3]]}])
; => [{"id" 1, "owner" "Eve", "credits" 100}
;     {"id" 3, "owner" "Dan", "credits" 50}]

; to count
(client ["count" "accounts" {"where" ["=" "vip" true]}])
; => 1

; to delete
(client ["delete" "accounts" {"where" ["=" "id" 3]}])
; => 1

; and lastly to update
(client ["update" "accounts" {"credits" 55} {"where" ["=" "owner" "Bob"]}])
; => 1