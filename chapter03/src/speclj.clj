(require '[speclj.core :refer :all])

;behavior
(describe "Mathematics"

  (it "1 plus 1 equals 2"
    (should 
    	(= 2 (+ 2 1))))

  (it "1 plus 1 is not greater than 3"
    (should-not 
    	(< 3 (+ 1 1)))))

; run the specs
(run-specs)