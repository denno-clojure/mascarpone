(ns orbit.ui.configure-element
  (:use [orbit.ui.tools :only [color]])
  (:require (orbit [configure :as c])
            (orbit.ui [effect :as effect]))
  (:import de.lessvoid.nifty.controls.Controller
           de.lessvoid.nifty.tools.Color
           (de.lessvoid.nifty.builder
             ControlBuilder
             ElementBuilder
             ImageBuilder
             PanelBuilder
             ScreenBuilder
             TextBuilder)))

(defn margin! [^ElementBuilder el m]
  (cond
    (number? m) (.margin el (str m))
    (and (sequential? m)
         (= (count m) 4))
    (let [[top right bottom left] m]
      (doto el
        (.marginTop top)
        (.marginRight right)
        (.marginBottom bottom)
        (.marginLeft left)))))

(defn padding! [^ElementBuilder el p]
  (cond
    (number? p) (.margin el (str p))
    (and (sequential? p)
         (= (count p) 4))
    (let [[top right bottom left] p]
      (doto el
        (.paddingTop top)
        (.paddingRight right)
        (.paddingBottom bottom)
        (.paddingLeft left)))))

(defn add-item [^ElementBuilder el item]
  (condp instance? item
    ImageBuilder (.image el item)
    PanelBuilder (.panel el item)
    TextBuilder  (.text el ^TextBuilder item)
    ControlBuilder (.control el item)))

(defn add-items [el items]
  (doseq [item items]
    (add-item el item)))

(defmacro add-effect [fn effects]
  `(doseq [effect# ~effects]
     (if effect#
       (~fn ~'el (effect/into-effect effect#)))))

(defn add-effects [^ElementBuilder el effects]
  (let [{:keys [active click custom end-hover
                end-screen focus get-focus
                hide hover lost-focus show
                start-hover start-screen]} effects]
    (add-effect .onActiveEffect active)
    (add-effect .onClickEffect click)
    (add-effect .onCustomEffect custom)
    (add-effect .onEndHoverEffect end-hover)
    (add-effect .onEndScreenEffect end-screen)
    (add-effect .onFocusEffect focus)
    (add-effect .onGetFocusEffect get-focus)
    (add-effect .onHideEffect hide)
    (add-effect .onHoverEffect hover)
    (add-effect .onLostFocusEffect lost-focus)
    (add-effect .onShowEffect show)
    (add-effect .onStartHoverEffect start-hover)
    (add-effect .onStartScreenEffect start-screen)))

(defn configure-element-builder
  [^ElementBuilder this params]
  (c/configure-helper
    params param
    :background (.backgroundColor
                  this ^Color (color param))
    :background-image (.backgroundImage this param)
    :color (.color this ^Color (color param))
    :controller (.controller this ^Controller param)
    :effects (add-effects this param)
    :focusable? (.focusable this (boolean param))
    :font (.font this param)
    :halign (case param
              :center (.alignCenter this)
              :left (.alignLeft this)
              :right (.alignRight this))
    :height (.height this (str param))
    :id (.id this (name param))
    :inset (.inset this (str param))
    :items (add-items this param)
    :layout (case param
              :absolute (.childLayoutAbsolute this)
              :absolute-inside (.childLayoutAbsoluteInside this)
              :center (.childLayoutCenter this)
              :horizontal (.childLayoutHorizontal this)
              :overlay (.childLayoutOverlay this)
              :vertical (.childLayoutVertical this))
    :margin (margin! this param)
    :name (.name this param)
    :padding (padding! this param)
    :selection-color (.selectionColor
                       this ^Color (color param))
    :style (.style this param)
    :valign (case param
              :center (.valignCenter this)
              :bottom (.valignBottom this)
              :top (.valignBottom this))
    :visible? (.visible this (boolean param))
    :width (.width this (str param))
    :x (.x this (str param))
    :y (.y this (str param))
    ))

(extend-type ElementBuilder
  c/Configurable
  (configure [this params]
    (configure-element-builder this params)))

(extend-type TextBuilder
  c/Configurable
  (configure [this params]
    (configure-element-builder
      this (dissoc params :wrap?))
    (.wrap this (boolean (:wrap? params)))))

(extend-type ScreenBuilder
  c/Configurable
  (configure [this params]
    (c/configure-helper
      params param
      :controller (.controller this param)
      :focus (.defaultFocusElement this param)
      :id nil
      :items (doseq [layer param]
                (.layer this layer)))))
