(use '[json-path])

(at-path "$.hello" {:hello "world"}) 
; => "world"
(at-path "$.hello.world" {:hello {:world "foo"}}) 
; => "foo"
(at-path "$..world" {:hello {:world "foo"},
                   :baz {:world "bar",
                           :quuz {:world "zux"}}})
; => ["foo", "bar", "zux"]
(at-path "$.*.world" {:a {:world "foo"},
                    :b {:world "bar",
                        :c {:world "baz"}}})
; => ["foo", "bar"]
(at-path "$.foo[*]" {:foo ["a", "b", "c"]})
; => ["a", "b", "c"]
(at-path "$.foo[?(@.bar=\"baz\")].hello"
       {:foo [{:bar "wrong" :hello "goodbye"}
              {:bar "baz" :hello "world"}]})
; => ["world"]
(at-path "$.foo[?(@.id=$.id)].text"
       {:id 45, :foo [{:id 12, :text "bar"},
                      {:id 45, :text "hello"}]})
; => ["hello"])


; with cheshire, let's make sure we have keywords generate for us when parsing
(use '[cheshire.core])
(at-path "$.foo" (parse-string "{\"foo\":\"bar\"}" (fn [k] (keyword k)))))