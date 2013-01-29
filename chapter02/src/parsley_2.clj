(require '[net.cgrand.parsley :as p])

(def p (p/parser :expr #{"x" "\n" ["(" :expr* ")"]}))
; #'net.cgrand.parsley/p

(let [line (apply str "\n" (repeat 10 "((x))"))
         input (str "(" (apply str (repeat 1000 line)) ")")
         buf (p/incremental-buffer p)
         buf (p/edit buf 0 0 input)]
     (time (p/parse-tree buf))
     (time (p/parse-tree (-> buf (p/edit 2 0 "(") (p/edit 51002 0 ")"))))
     nil)
; "Elapsed time: 508.834 msecs"
; "Elapsed time: 86.038 msecs"
; nil
