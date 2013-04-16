(ns test-1.core-spec
  (:use
    [speclj.core]
    [joodo.spec-helpers.controller]
    [test-1.core]))

(describe "test-1"

  (with-mock-rendering)
  (with-routes app-handler)

  (it "handles home page"
    (let [result (do-get "/")]
      (should= 200 (:status result))
      (should= "index" @rendered-template)))
  )

(run-specs)
