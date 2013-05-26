(ns friendly.server
  (:require [noir.server :as server]
            [cemerick.friend :as friend]
            [cemerick.friend.credentials :as creds]
            [cemerick.friend.workflows :as workflows]
            [noir.fetch.remotes :as remotes]))

(def users {"user" {:username "user"
                    :password (creds/hash-bcrypt "pass")
                    :roles #{::user}}})

(defn fetch-workflow [request]
  (if (= "/_fetch" (:uri request))
    (let [{:keys [remote params]} (:params request)
          [{:keys [user pass]}] (remotes/safe-read params)]
      (if (= remote "login")
        (if-let [user-record ((:credential-fn  (::friend/auth-config request))
                              ^{::friend/workflow :fetch-workflow}
                              {:username user :password pass})]
          (workflows/make-auth user-record
                               {::friend/workflow :fetch-workflow
                                ::friend/redirect-on-auth? false})
          {:status 401 :headers {"Content-Type" "text/plain"}
           :body ""})))))

(server/load-views-ns 'friendly.views)

(server/add-middleware friend/authenticate
                       {:credential-fn (partial creds/bcrypt-credential-fn users)
                        :workflows [#'fetch-workflow]
                        :unauthorized-handler
                        (constantly
                         {:status 401
                          :body (pr-str
                                 "Sorry, you do not have access to this resource.")})})

(defn fetch-logout [handler]
  (fn [request]
    (if (and (= "/_fetch" (:uri request))
             (= "logout" (:remote (:params request))))
      ((friend/logout handler) request)
      (handler request))))

(server/add-middleware fetch-logout)

(defn -main [& m]
  (let [mode (keyword (or (first m) :dev))
        port (Integer. (get (System/getenv) "PORT" "8080"))]
    (server/start port {:mode mode
                        :ns 'friendly})))

