(ns chapter05_06.routes.home
  (:use compojure.core)
  (:require [chapter05_06.views.layout :as layout]
            [chapter05_06.util :as util]))

(defn home-page []
  (layout/render
    "home.html" {:content (util/md->html "/md/docs.md")}))

(defn about-page []
  (layout/render "about.html"))

(defroutes home-routes
  (GET "/" [] (home-page))
  (GET "/about" [] (about-page)))
