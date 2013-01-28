(use 'clj-pdf.core)

; (pdf <in> <out>)
(pdf 
  [{}
   [:list {:roman true} [:chunk {:style :bold} "a bold item"] "another item" "yet another item"]   
   [:phrase "some text"]
   [:chart {:type "bar-chart" :title "Bar Chart" :x-label "Items" :y-label "Quality"} [2 "Foo"] [4 "Bar"] [10 "Baz"]]
   [:phrase "some more text"]
   [:paragraph "yet more text"]]
  "doc.pdf")  