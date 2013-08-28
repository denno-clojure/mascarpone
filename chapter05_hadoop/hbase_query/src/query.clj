(use '[cascalog api playground])
(use '[hbase.cascalog core ops])

; table-name key-field column-family & value-fields
(let [table (hbase-tap "test-users" "testrow" "test-cf-name" "test")]
  ; compose and execute a query: (?<- out-tap out-vars & predicates)
  (?<- 								
    ; output to stdout
  	(stdout) 						
  	; two values to compute
  	[?id ?c] 						
  	; compute ?id, the key of the row
  	(table ?id ?test-cf-name) 		
  	; compute ?c. returned as bytes
  	(slurp ?test-cf-name :> ?c))) 	