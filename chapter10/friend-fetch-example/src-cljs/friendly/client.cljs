(ns friendly.client
  (:require [fetch.remotes :as remotes]
            [goog.dom :as dom])
  (:require-macros [fetch.macros :as fm]))

(defn ^:export login []
  (let [user (.-value (dom/getElement "user"))
        pass (.-value (dom/getElement "pass"))]
    (fm/remote (login {:user user :pass pass}) [result]
               (if result
                 (dom/setTextContent (dom/getElement "currentuser") result)))))

(defn ^:export logout []
  (fm/remote (logout) [result]
             (dom/setTextContent (dom/getElement "currentuser") "")))

(defn ^:export another []
  (fm/remote (another) [result]
             (js/alert result)))

(defn ^:export setuser []
  (fm/remote (get-user) [result]
             (dom/setTextContent (dom/getElement "currentuser") result)))