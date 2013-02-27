 (use 'factql.core)

 (init! "JpyEkmTYhsGLylSWV2Yws9aCa8e8q232WPCbVJIW" "VkrTk8gENHLH8rOzcdPz4i81EJGsyKI13CQ4CUvR")

; find beers from Japan
(select beers (where (like :country "japan")))

; beers from Ibaraki
(select beers (where (like :country "japan") (like :state "Ibaraki*")))

; find kiuchi beers from Japan
(select beers (where 
	(like :country "japan") 
	(like :brewery "Kiuchi")))

; Tokyo beers
(select beers (where 
	(like :country "japan") 
	(like :state "Tōkyō-to")))