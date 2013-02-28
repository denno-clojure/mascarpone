; https://github.com/clojure/core.logic/wiki/Examples

(ns logic1
   (:refer-clojure :exclude [==])
   (:use clojure.core.logic))

; conde
; "Logical disjunction of the clauses. The first goal in
; a clause is considered the head of that clause. Interleaves the
; execution of the clauses."

; defne
; "Define a goal fn. Supports pattern matching. All
; patterns will be tried. Based on conde"
(defne moveo 
  [before action after]
  ([
    [:middle :onbox :middle :hasnot]
    :grasp
    [:middle :onbox :middle :has]
  ])
  ([
    [pos :onfloor pos has] 
    :climb
    [pos :onbox pos has]
  ])
  ([
    [pos1 :onfloor pos1 has]
    :push
    [pos2 :onfloor pos2 has]
    ; pos2 will be resolved through the pattern matching
  ])
  ([
    [pos1 :onfloor box has]
    :walk
    [pos2 :onfloor box has]
  ]))


(defne cangeto [state out]
  ; if has the object returns true
  ([
    [_ _ _ :has] 
    true])
  ( 
    [_ _]
    (fresh [action next]
           (moveo state action next)
           (trace-lvars ":>" state action) ; trace the result
           (cangeto next out))))

; there are two useful macros for core logic:
; run, that looks for n solutions, here 1
(run 1 [q]
  (cangeto [:atdoor :onfloor :atwindow :hasnot] q)) 
; (true)

; With the following sequence being the answer:
; (:>
; state = [:atdoor :onfloor :atwindow :hasnot]
; action = :walk
; :>
; state = [:atwindow :onfloor :atwindow :hasnot]
; action = :climb
; :>
; state = [:atwindow :onfloor :atwindow :hasnot]
; action = :push
; :>
; state = [_0 :onfloor :atwindow :hasnot]
; action = :walk
; :>
; state = [_0 :onfloor _0 :hasnot]
; action = :climb
; :>
; state = [:atwindow :onfloor :atwindow :hasnot]
; action = :climb
; :>
; state = [_0 :onfloor _0 :hasnot]
; action = :push
; :>
; state = [:atwindow :onfloor :atwindow :hasnot]
; action = :push
; :>
; state = [:middle :onbox :middle :hasnot]
; action = :grasp
; :>
; state = [_0 :onfloor :atwindow :hasnot]
; action = :walk
; :>
; state = [_0 :onfloor _0 :hasnot]
; action = :walk
; true)