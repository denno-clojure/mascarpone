(ns orbit.ui.element
  (:require (orbit [configure :as c]
                    [tree :as tree]
                    [util :as util])
            (orbit.ui configure-element controls))
  (:import (de.lessvoid.nifty.builder
             ControlBuilder
             ElementBuilder
             ImageBuilder
             LayerBuilder
             PanelBuilder
             PopupBuilder
             ScreenBuilder
             TextBuilder)
           de.lessvoid.nifty.controls.button.builder.ButtonBuilder
           de.lessvoid.nifty.controls.checkbox.builder.CheckboxBuilder
           de.lessvoid.nifty.controls.console.builder.ConsoleBuilder
           de.lessvoid.nifty.controls.dropdown.builder.DropDownBuilder
           de.lessvoid.nifty.controls.imageselect.builder.ImageSelectBuilder
           de.lessvoid.nifty.controls.label.builder.LabelBuilder
           de.lessvoid.nifty.controls.listbox.builder.ListBoxBuilder))

(defn element
  "Create an element.
  
  Types:
   :image
   :layer
   :panel
   :popup
   :screen *
   :text
  
  Types (controls):
   :button *
   :checkbox
   :console *
   :drop-down *
   :image-select *
   :label
   :list-box
  
  * Requires the :id option.
  
  
  Options (except for :screen - see below):
   :background - a color
   :background-image
   :layout :absolute :absolute-inside :center
           :horizontal :overlay or :vertical
   :color
   :controller
   :focusable?
   :font  a string pointing to a font on
          the classpath.
   :halign  :center :left or :right
   :height
   :id
   :inset
   :items - a sequence of elements to
            add as children to this element.
   :margin
   :name
   :padding
   :selection-color
   :style
   :valign
   :visible?
   :width
   :x
   :y
  
  Options, for :screen type:
   :controller
   
   :focus  id of the element to focus when
           the screen is shown.
   
   :items  A list/vector of layers to show in this screen.
           Layers are ordered back to front. That is,
           the first element in the list is in the back.
           The second element is in front of the first.
           The third element is in front of the second.
           And so on.
  "
  {:arglists '([type & options])}
  [type & {:as options}]
  (let [get-id (fn []
                 (or (:id options)
                     (util/arg-err
                       ":id option required for element type "
                       type)))
        options (if (not= (name type) "screen")
                  (merge {:layout :horizontal}
                         options)
                  options)
        builder
        (case (name type)
          "image"  (ImageBuilder.)
          "layer"  (LayerBuilder.)
          "panel"  (PanelBuilder.)
          "popup"  (PopupBuilder.)
          "screen" (ScreenBuilder. (get-id))
          "text"   (TextBuilder.)
          ;; controls
          "button" (ButtonBuilder. (get-id))
          "checkbox" (CheckboxBuilder.)
          "console" (ConsoleBuilder. (get-id))
          "drop-down" (DropDownBuilder. (get-id))
          "image-select" (ImageSelectBuilder. (get-id))
          "label" (LabelBuilder.)
          "list-box" (ListBoxBuilder.)
          (util/arg-err "invalid element type:" type))]
    (c/conf-int builder options)))

(defn into-object [m]
  (let [{:keys [selector options children]} m
        {:keys [id type]} selector
        options (cond-> options
                        (and id (not (:id options)))
                         (assoc :id id)
                        true
                         (assoc :items (map :object children)))]
    (apply element type (reduce concat options))))

(defn into-element
  "Convert element into an element.
  
  Element can either be:
   Of type ElementBuilder. This is what you get
   from calling orbit.ui/element.
   
   A vector.
  "
  {:arglists '([element])}
  [el]
  (cond
    (instance? ElementBuilder el) el
    (instance? ScreenBuilder el) el
    (vector? el) (tree/vec->node el into-object)
    :else (util/convert-err el)))
