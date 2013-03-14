(ns chapter05_06.views.layout
  (:use noir.request)
  (:require [clabango.parser :as parser]))

(def template-path "chapter05_06/views/templates/")

(defn render [template & [params]]
  (parser/render-file (str template-path template)
                      (assoc params :context (:context *request*))))

