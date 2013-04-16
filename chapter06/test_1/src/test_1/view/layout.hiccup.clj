(doctype :html5)
[:html
 [:head
  [:meta {:http-equiv "Content-Type" :content "text/html" :charset "iso-8859-1"}]
  [:title "test-1"]
  (include-css "/stylesheets/test_1.css")
  (include-js "/javascript/test_1.js")]
 [:body
  (eval (:template-body joodo.views/*view-context*))
]]