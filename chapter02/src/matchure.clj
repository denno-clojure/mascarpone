(use 'matchure')

; basic usage
(if-match [nil nil] true) ;=> true
(if-match [1 1] true) ;=> true

; conditional match

(cond-match "hello, world"
  #"foo" "matches foo"
  #"hello" "matches hello"
  ? "doesn't match either") ;=> "matches hello"

; multiple condition can match(let [s "hello world"]
(cond-match
 [#"foo" s] "matches foo"
 [#"hello" s] "matches hello"
 [? s] "doesn't match either")) ;=> "matches hello"
