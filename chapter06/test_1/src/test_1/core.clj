(ns test-1.core
  (:use
    [compojure.core :only (defroutes GET)]
    [compojure.route :only (not-found)]
    [joodo.middleware.view-context :only (wrap-view-context)]
    [joodo.views :only (render-template render-html)]
    [joodo.controllers :only (controller-router)]))

(defroutes test-1-routes
  (GET "/" [] (render-template "index"))
  (controller-router 'test-1.controller)
  (not-found (render-template "not_found" :template-root "test_1/view" :ns `test-1.view.view-helpers)))

(def app-handler
  (->
    test-1-routes
    (wrap-view-context :template-root "test_1/view" :ns `test-1.view.view-helpers)))

