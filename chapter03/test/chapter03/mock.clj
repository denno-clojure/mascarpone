(ns mock
  (:use clojure.test
        ring.mock.request))

(defn your-handler [request]
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body "Hello World."})

(deftest your-handler-test
  (is (= (your-handler (request :get "/doc"))
         {:status 200
          :headers {"Content-Type" "text/plain"}
          :body "Hello World."})))