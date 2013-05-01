(ns music.test.chord
  (:use [music.chord])
  (:use [music.inverter])
  (:use [clojure.test]))

(deftest test-triad
    (is (= [:C :E :G] (triad C 0))) 
    (is (= [:E :G :B] (triad C 2))) 
)

(deftest test-seventh
    (is (= [:C :E :G :B] (seventh_chord C 0))) 
    (is (= [:E :G :B :D] (seventh_chord C 2))) 
)

(deftest test-chord
    (is (= [:D :F :A :C :E] (chord 5 C 1)))
    (is (= [:D :F :A :C :E] (chord 5 C :D)))
)

(deftest test-intervals
    (is (= [:C :G :D :A :E :B :F] (take 7 (intervals C 5))))
    (is (= [:C :E :G :B :D :F :A :C :E] (take 9 (intervals C 3))))
    (is (= [:C :D :E] (take 3 (intervals C 2))))
    (is (= [:C :C :C] (take 3 (intervals C 1))))
    (is (empty? (take 3 (intervals C 0))))
)

(deftest test-scale-stepper
    (is (= [:C :C :D :E :E :F :G :G :A :B :B :C] 
        (take 12 (scale-stepper C [0 1 1])))
    ))

(deftest test-step
    (is (= [2 3 1] (step [1 2 3])))
    (is (= [3 1 2] (step [1 2 3] 2)))
    (is (= [1 2 3] (step [1 2 3] 3)))
    (is (= [2 3 1] (step [1 2 3] 4)))
)

(deftest test-inverter
    (is (= 
        '([:C :E :G] [:D :F :B] [:E :A :C] [:G :B :D] [:A :C :F] [:B :E :G] [:D :F :A])
        (inverter [1 1 2] [1 3 5] C))))

(deftest test-inverter-descending
    (is (= 
        '([:C :E :G] [:A :D :F] [:G :B :E] [:F :A :C] [:D :G :B] [:C :E :A] [:B :D :F])
        (inverter [-2 -1 -1] [1 3 5] C))))


(deftest test-transposer
    (is (= [:E :F :G :A :B :C :D] (transposer C :E)))
    (is (= [:E :F :G :A :B :C :D] (transposer C 2)))
    (is (= [:B :C :D :E :F :G :A] (transposer C -1))))
