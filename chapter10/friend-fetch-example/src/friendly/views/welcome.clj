(ns friendly.views.welcome
  (:require [friendly.views.common :as common]
            [cemerick.friend :as friend])
  (:use [noir.fetch.remotes]
        [noir.core :only [defpage]]))

(defremote get-user []
  (:username (friend/current-authentication)))

(defremote login [auth]
  (friend/authorize #{:friendly.server/user}
                    (:username (friend/current-authentication))))

(defremote logout [] nil)

(defremote another []
  (friend/authorize #{:friendly.server/user} "This action required logging in!"))

(defpage "/" []
  (common/layout
   [:p "Logged in as: " [:span#currentuser]]
   [:p "try user:pass"]
   [:p
    [:label {:for "user"} "User"]
    [:br]
    [:input#user {:type "text" :name "user"}]
    [:br]
    [:label {:for "pass"} "Password"]
    [:br]
    [:input#pass {:type "password" :name "pass"}]
    [:br]
    [:input {:type "submit" :onclick "friendly.client.login();"
             :value "Login"}]
    [:br]
    [:br]
    [:input {:type "submit" :onclick "friendly.client.another();"
             :value "Requires Authorization"}]
    [:br]
    [:input {:type "submit" :onclick "friendly.client.logout();"
             :value "Logout"}]]))