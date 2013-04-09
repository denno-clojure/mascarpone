(use '[cascalog api playground])
(use '[hbase.cascalog core ops])

; table-name key-field column-family & value-fields
(let [table (hbase-tap "test-users" "testrow" "test-cf-name" "test")]
  (?<- 								; compose and execute a query: (?<- out-tap out-vars & predicates)
  	(stdout) 						; output to stdout
  	[?p ?c ?a] 						; two values to compute
  	(table ?p ?test-cf-name) 		; compute ?p
  	(slurp ?test-cf-name :> ?c))) 	; compute ?c. returned as bytes