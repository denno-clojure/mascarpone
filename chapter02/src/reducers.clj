(require '[clojure.core.reducers :as r])

(r/map inc [1 1 1 2])
; => #<reducers$folder$reify__6531 clojure.core.reducers$folder$reify__6531@dd4fe75>

; reduce forces the computation
(reduce + (r/filter even? (r/map inc [1 1 1 2])))
; => 6

; create a composite function, that 
; - increase each value of a collection
; - filter even numbers
(def red (comp (r/filter even?) (r/map inc))) 
; => #<core$comp$fn__4092 clojure.core$comp$fn__4092@13f4fcb8>

; compute the result on reduce
(reduce + (red [1 1 1 2]))
; [1 1 1 2]
; apply inc: [2 2 2 3]
; filter even?: [2 2 2]
; reduce with +: 2 + 2 + 2 
; result: 6