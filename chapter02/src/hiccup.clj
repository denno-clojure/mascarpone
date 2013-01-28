(use 'hiccup.core)

(html [:span {:class "foo"} "bar"])
; "<span class=\"foo\">bar</span>"

(html [:div#foo.bar.baz "bang"])
; "<div id=\"foo\" class=\"bar baz\">bang</div>"