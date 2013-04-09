(use '[cascalog api playground])
(use '[hbase.cascalog core ops])

(let [table (hbase-tap "test-users" "testrow" "test-cf-name" "test")]
  (?<- 							
  	(stdout) 					
  	[?p ?c ?a] 					
  	(table ?p ?test-cf-name) 	
  	(slurp ?test-cf-name :> ?c)
  	(count ?c :> ?a) ; for a, count the letters of the value of column ?c
  	))