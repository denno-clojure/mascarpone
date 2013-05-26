#!/bin/bash lein-exec

(use '[leiningen.exec :only  (deps)])

(deps '[[ring "1.2.0-beta1"]])
(deps '[[compojure "1.1.5"]])

(use 'ring.middleware.resource
     'ring.middleware.file-info)
(use 'ring.adapter.jetty)
(require '[ring.util.response :as response])
(use 'compojure.core)
(require '[compojure.route :as route])

; custom sample routes:
(defroutes app
  (GET "/" [] "<h1>Hello World</h1>")
  (GET "/message" [] "<screen_message>Hello</screen_message>")
  (GET "/hello.xml" [] (response/redirect "/message"))
  (route/not-found "<h1>Page not found</h1>"))

(def routed-app
 (-> app
     (wrap-resource ".")))

(run-jetty routed-app {:port 3000})