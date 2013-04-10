(use '[cascalog api playground])
(use '[hbase.cascalog core ops])

; table-name key-field column-family & value-fields
(let [table (hbase-tap "test-users" "testrow" "test-cf-name" "test")]
  (?<- 								; compose and execute a query: (?<- out-tap out-vars & predicates)
  	(stdout) 						; output to stdout
  	[?id ?c] 						; two values to compute
  	(table ?id ?test-cf-name) 		; compute ?id, the key of the row
  	(slurp ?test-cf-name :> ?c))) 	; compute ?c. returned as bytes