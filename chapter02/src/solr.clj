(use 'clojure-solr)

(with-connection (connect "http://127.0.0.1:8983/solr")
  (add-document! {"id" "testdoc", "name" "A Test Document"})
  (add-documents! [{"id" "testdoc.2", "name" "Another test"}
                               {"id" "testdoc.3", "name" "a final test"}])
  (commit!)
  )

(with-connection (connect "http://127.0.0.1:8983/solr")
	; (search "test" {:rows 2})
	(search "test" {}))