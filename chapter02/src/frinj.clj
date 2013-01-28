(use '[frinj.core])
(use '[frinj.calc])

; init constants and computations
(frinj-init!)

; convert from volume to unit
(-> (fj 10 :feet 12 :feet 8 :feet :to :liters) str)