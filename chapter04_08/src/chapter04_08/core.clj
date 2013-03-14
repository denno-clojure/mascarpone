(ns chapter04-08.core
 (:use [liberator.core])
 (:use [compojure.core]))

(def postbox-counter (atom 0))

(defresource postbox
  :available-media-types ["text/html" "text/plain"]
  :method-allowed? (request-method-in :post :get)
  :post! (fn [_] (swap! postbox-counter inc))
  :handle-created (fn [_] (str "Your submission was accepted. The counter is now " @postbox-counter))
  :handle-ok (fn [_] (str "The counter is " @postbox-counter)))

(defroutes app
  (ANY "/" [] (resource :available-media-types ["text/plain"] :handle-ok "Hello World!"))
  (ANY "/post" [] postbox))