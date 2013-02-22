(refer-clojure :exclude '[==])
(use 'clojure.core.logic)

(run* [q]
	(membero q [3 4 5])
	(membero q [3 4 1])
	(membero q [7 4 3])
	)
; returns 3 or 4
; (3 4)

; we search for a such that
(run* [q]
   (fresh [a]
   	 ; a is a member of [1 2 3]
     (membero a [1 2 3])
     ; q is a member of [3 4 5]
     (membero q [3 4 5])
     ; and a = q
     (== a q)))
; result is 
; 3