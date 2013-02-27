(use 'zenclient.core)

(create-account! "foo@bar.com") ; use your own e-mail address
; {:api-key "8333b48957189f68c2fc57364e75df16", :password "NEkDJZBWyzNj"}
(def job (create-job! "http://bit.ly/fzkUTT"))
; #'user/job
(job id)
; 991057
(->> job details finished?)
; false

; wait a bit

(->> job details finished?)
; true
(->> job details finished-at)
; #<DateTime 2011-01-12T11:09:20.000+01:00>
(clojure.java.browse/browse-url (->> job outputs first url))
; "https://zencoder-live.s3.amazonaws.com..."
