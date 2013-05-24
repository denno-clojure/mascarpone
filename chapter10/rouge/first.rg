(var RUBY_VERSION)
; 'ruby/RUBY_VERSION

(puts RUBY_VERSION)
; "1.9.3"

(puts (+ 1 2 3))
; 6

(puts (map class ["x" 4 {:z 9} 'quux]))
; (ruby/String ruby/Fixnum ruby/Hash ruby/Rouge.Symbol)

(puts (map inc [1 2 3 4]))
; (2 3 4 5)