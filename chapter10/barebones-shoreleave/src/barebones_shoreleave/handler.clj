(ns barebones-shoreleave.handler
  (:use
   barebones-shoreleave.middleware
   compojure.core)
  (:require
   [shoreleave.middleware.rpc :refer [defremote wrap-rpc]]
   [ring.middleware.anti-forgery]
   [compojure.handler :as handler]
   [compojure.route :as route]
   [net.cgrand.enlive-html :as html]))

;; Enlive template
(html/deftemplate main-layout
  "public/templates/index.html"
  [text]
  [:div#content] (html/html-content text))

;; https://github.com/shoreleave/shoreleave-remote-ring
(defremote ping [pingback]
  (str "You have hit the API with: " pingback))

;; Barely modified default routes
(defroutes app-routes
  (GET "/" []
       (main-layout "<a href='#' id='click'>Click me!</a>"))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (-> app-routes
      wrap-rpc
      wrap-add-anti-forgery-cookie
      ring.middleware.anti-forgery/wrap-anti-forgery
      handler/site))
