(use '[clisk live])

(def checker-pattern (checker 0 1)) 
(show  (scale 0.25 (offset (v* 6 vnoise) (v* vnoise checker-pattern)) ))