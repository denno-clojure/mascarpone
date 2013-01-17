(ns substance.core
  (:use [seesaw.core])
  (:import org.pushingpixels.substance.api.SubstanceLookAndFeel))

(defn laf-selector []
  (horizontal-panel
    :items ["Substance skin: "
            (combobox
              :model (vals (SubstanceLookAndFeel/getAllSkins))
              :renderer (fn [this {:keys [value]}]
                          (text! this (.getClassName value)))
              :listen [:selection (fn [e]
                                      ; Invoke later because CB doens't like changing L&F while
                                      ; it's doing stuff.
                                      (invoke-later
                                        (-> e
                                          selection
                                          .getClassName
                                          SubstanceLookAndFeel/setSkin)))])]))

(def notes " This example shows the available Substance skins. Substance
is a set of improved look and feels for Swing. To use it in a project,
you'll need to add a dep to your Leiningen project.")

(invoke-later
    (->
      (frame
        :title "Seesaw Substance/Insubstantial Example"
        :on-close :exit
        :content (vertical-panel
                   :items [(laf-selector)
                           (text :multi-line? true :text notes :border 5)
                           :separator
                           (label :text "A Label")
                           (button :text "A Button")
                           (checkbox :text "A checkbox")
                           (combobox :model ["A combobox" "more" "items"])
                           (horizontal-panel
                             :border "Some radio buttons"
                             :items (map (partial radio :text)
                                         ["First" "Second" "Third"]))
                           (scrollable (listbox :model (range 100)))]))
      pack!
      show!))