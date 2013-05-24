; switch to the activity namespace
(ns org.stuff.clojuroid.main)

; import the neko UI library namespaces
(use 'neko.resource 'neko.activity 'neko.notify 'neko.ui 'neko.threading 'neko.application)

; let's change the layout completely
 (on-ui
     (set-content-view! a
      (make-ui [:linear-layout {}
                [:text-view {:text (str "Hello from Clojure !" (java.util.Date.))}]])))


; let's define a new callback for a button
(defn some-callback [_]
  (toast "finished" :short))

; and change the ui with a button and an on-click listener
(on-ui
     (set-content-view! a
      (make-ui [:linear-layout {:layout-height :fill}
          [:button {:def hello :text "A button" :on-click some-callback :enabled true}]])))

; we can also change only the button
(on-ui 
  (config! :button hello :text "PRESS" :on-click 
    (fn[_] (toast "hello" :long))))


; get a resource from the application resources
(def icon-rsc (get-resource :drawable :ic-launcher))

; fire a notification using that resource
 (fire :new-mail
       (notification :icon  icon-rsc
                     :ticker-text "You've got mail"
                     :content-title "One new message"
                     :content-text "FROM: foo@bar.com"
                     :action [:activity "my.package.VIEW_MAIL"]))