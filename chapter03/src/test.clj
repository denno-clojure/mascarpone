; testing support
(use 'clojure.test)

; method to be tested
(defn say-hello[name]
	(str "bye " name))

; cannot great properly
(deftest a-test
  (testing "Greetings."
    (is (= "hello nico" (say-hello "nico")))))

; the test is a regular function so we can call it
; (a-test)


; we can also run all tests in this namespace
; (run-tests)