(require '[net.cgrand.parsley :as p])

; define our parser, based on a simple grammar
(def p (p/parser :expr #{"x" ["(" :expr* ")"]}))

; now run the grammar on some valid input
(pprint (p "(x(x))"))

; {:tag :net.cgrand.parsley/root,
;  :content
;    [{:tag :expr,
;      :content
;        ["("
;         {:tag :expr, :content ["x"]}
;         {:tag :expr, :content ["(" {:tag :expr, :content ["x"]} ")"]}
;         ")"]}]}

; running the same grammar on malformed input with garbage
(pprint (p "a(zldxn(dez)"))

; {:tag :net.cgrand.parsley/unfinished,
;  :content
;    [{:tag :net.cgrand.parsley/unexpected, :content ["a"]}
;     {:tag :net.cgrand.parsley/unfinished,
;      :content
;        ["("
;         {:tag :net.cgrand.parsley/unexpected, :content ["zld"]}
;         {:tag :expr, :content ["x"]}
;         {:tag :net.cgrand.parsley/unexpected, :content ["n"]}
;         {:tag :expr,
;          :content
;            ["("
;             {:tag :net.cgrand.parsley/unexpected, :content ["dez"]}
;             ")"]}]}]}