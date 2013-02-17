(use '[gajure.core])

(comment
  "This provides an example use of the GA framework. We evolve the string helloworld.")


; basically our inputs to the genetic algorithm run
; dna is the range of items available
(def dna (map str (seq "qwertyuiopasdfghjklzxcvbnm .")))

; the expected result turned into a sequence of characters
; this should not really be not known, since this is what we are looking for
(def sseq (map str (seq "hello Japan")))
; the length as a variable, to make it easier to read
(def len (count sseq))

; compare the fitness of our result to the expected result
; this could be a black box, meaning a function we do not 
; have the code but we can use to have the result

; return a binary comparison result between the expectation and 
; our random test string
(defn fitness-fn-in[lst sequence]
  (map #(if (= %1 %2) 1 0) lst sseq))
; return the number of errors (non-fit) found in the test string
(defn fitness-fn
     [lst]
     (reduce + (fitness-fn-in lst sseq)))

; this return random sets based on the dna we have
; the returned function has one argument, the number of sets to generate 
(defn init-fn
  [] (partial rand-pop dna len))

(def func-map {
  :init-fn (init-fn)
  :fit-fn fitness-fn
  :mut-fn generic-mutation
  :sel-fn roulette-select 
  :cross-fn list-crossover})

(def set-map {
  :pop-sz 200 ; the size of the population
  :children 180 ; how many new children in each generation
  :mut-r 1 ; mutation rate 
  :gen 1000 ; how many rounds
})

(run-ga func-map set-map)