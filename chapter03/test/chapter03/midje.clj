(ns chapter03.midje
  (:use [midje.sweet]))

; simple check
(fact 
  (+ 1 1) => 2)

; same with a description
(fact "addition has a unit element"
  (+ 12345 0) => 12345)

; regexp string matching
(fact
  "O wad some Pow'r the giftie gie us. To see oursels as ithers see us!"
  => #"giftie")

; partial function 
(fact
  (+ 1 1) => even?)

; collection contains an element
(defn function-that-returns-a-collection []
  [4])
(fact
  (function-that-returns-a-collection) => (contains 5))