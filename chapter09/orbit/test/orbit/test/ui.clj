(ns orbit.test.ui
  (:use orbit.core
        (orbit.ui element))
  (:require [orbit.core :as mkr]))

(defn test1 []
  (.setLevel (java.util.logging.Logger/getLogger "")
             java.util.logging.Level/WARNING)
  (mkr/application
    :init
    (fn [app]
      (let [n (nifty-display app)
            ni (nifty n)
            s (into-element
                [:screen#start
                 [:layer
                  [:panel {:width "10%" :height "10%"
                           :background "#f00"}]
                  [:panel {:width "10%" :height "20%"
                           :background "#0f0"}]
                  [:panel {:width "10%" :height "30%"
                           :background "#00f"}]]])]
        (.addScreen ni "start" (.build s ni))
        (.gotoScreen ni "start")))
    :settings {:vsync true}))

(defn test2 []
  (.setLevel (java.util.logging.Logger/getLogger "")
             java.util.logging.Level/WARNING)
  (mkr/application
    :init
    (fn [app]
      (let [n (nifty-display app)
            ni (nifty n)
            s (into-element
                [:screen#start
                 [:layer
                  [:label {:label "hello world!"}]]])]
        (.loadStyleFile ni "nifty-default-styles.xml")
        (.loadControlFile ni "nifty-default-controls.xml")
        (.addScreen ni "start" (.build s ni))
        (.gotoScreen ni "start")))
    :settings {:vsync? true}))

(defn test3 []
  (.setLevel (java.util.logging.Logger/getLogger "")
             java.util.logging.Level/WARNING)
  (mkr/application
    :init
    (fn [app]
      (ui app
          :screens [[:screen#start
                     [:layer#background.test-class {:layout :center}
                      [:panel#inner.test-class
                       {:height "50%"
                        :background "#f00"
                        :padding "10px"
                        :margin "10px"
                        :effects
                        {:start-screen
                         [[:border :parameters
                           {:color "#ff0f"
                            :border "10px"
                            :length "infinite"}]
                          [:fade]]
                         }}
                       [:panel {:padding "10px" :background "#f0f"}
                        [:panel {:width "10px"
                                 :height "10px"
                                 :margin "50px"
                                 :background "#583"}]
                        [:label.test-class2 {:label "hello world!"
                                             :margin "20px"}]]
                       [:label {:label "hello!"}]]]]]
          :style (list [:#background :background "#00f"]
                       [:#inner :width "20%"]
                       [:.test-class :layout :horizontal]
                       [:.test-class2
                        :effects {:start-screen
                                  [[:border :parameters
                                    {:color "#333"
                                     :border "5px"
                                     :length "infinite"}]]}])
          :start-screen :start))
    :settings {:vsync? true}))

; (defn test-styles []
;   (style
;     [:#b-id.a-class.b-class
;      :a 3 :b 4
;      (list :x 10 :y 12)
;      [:.sub-class
;       :x :bla]]
;     [:#a-id.a-class :x 5 :y 6 :a 4]
;     [:#a-id.b-class :stuff "hello" :a 5]
;     ))
