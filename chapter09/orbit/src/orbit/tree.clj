(ns orbit.tree
  (:require (orbit [configure :as c]
                    [util :as util])
            [clojure.string :as cstr]))

(defn split-id-class-keyword [k]
  (let [n (name k)
        type (re-find #"^[^\.#]+" n)
        id (re-find #"(?<=#)[^\.]+" n)
        classes-str (re-find #"(?<=\.)[^#]+$" n)
        classes (if classes-str
                  (cstr/split classes-str #"\.")
                  '())]
    {:type type
     :id id
     :classes classes}))

(defn into-selector [obj]
  (if (keyword? obj)
    (split-id-class-keyword obj)
    obj))

(defn flatten-children [children]
  (reduce (fn [v n]
            (if (list? n)
              (vec (concat v n))
              (conj v n)))
          [] children))

;; =====
;; Tree
;; =====
(defn into-find-map [selector m]
  (let [{:keys [id classes]} selector]
    {:classes (into {} (map #(vector % [m]) classes))
     :ids (if id (assoc {} id m))}))

(defn merge-find-map [m1 m2]
  {:classes (merge-with concat
                        (:classes m1)
                        (:classes m2))
   :ids (merge (:ids m1)
               (:ids m2))})

(defn merge-find-maps [ms]
  (reduce merge-find-map ms))

(defn find-in-find-map [find-map selector])

(defn vec->node [v into-object]
  (let [[k & more] v
        [options & children] (if (map? (first more))
                               more (cons {} more))
        {:keys [id type classes] :as selector}
         (into-selector k)
        children (map #(vec->node % into-object)
                      (flatten-children children))
        m {:selector selector
           :children children
           :options options}
        object (into-object m)
        find-map (into-find-map selector (gensym))
        find-maps (cons find-map (map :find-map children))]
    (assoc m
      :find-map (merge-find-maps find-maps)
      :object object)))

;; =====
;; Style
;; =====

;; Creating

(declare merge-styles vec->style)

(defn vec->style-map [more]
  (let [{sub-styles true options false}
        (group-by vector? more)
        options (flatten-children options)
        sub-styles (map vec->style sub-styles)
        sub-style (reduce merge-styles sub-styles)]
    {:options (apply hash-map options)
     :sub-style sub-style}))

(defn vec->style [style]
  (let [[k & more] style
        style-map (vec->style-map more)
        {:keys [type id classes]} (into-selector k)]
    {:types (if type (assoc {} type style-map))
     :ids (if id (assoc {} id style-map))
     :classes (into {} (map #(vector % style-map)
                            classes))}))

(defn merge-style-map
  ([] nil)
  ([sm] sm)
  ([sm1 sm2]
   (let [{opts1 :options
          sub1 :sub-style} sm1
         {opts2 :options
          sub2 :sub-style} sm2]
     {:options (merge opts1 opts2)
      :sub-style (merge-styles sub1 sub2)}))
  ([sm1 sm2 sm3 & more]
   (apply merge-style-map
          (merge-style-map sm1 sm2)
          sm3 more)))

(defn merge-styles
  ([] nil)
  ([style] style)
  ([style1 style2]
   (let [{ids1 :ids classes1 :classes} style1
         {ids2 :ids classes2 :classes} style2]
     {:types (merge-with merge-style-map
                         (:types style1) (:types style2))
      :ids (merge-with merge-style-map ids1 ids2)
      :classes (merge-with merge-style-map classes1 classes2)}))
  ([style1 style2 & more]
   (let [s (merge-styles style1 style2)]
     (apply merge-styles s more))))

(defn into-style
  [& styles]
  (cond
    (map? (first styles)) (first styles)
    :else (let [styles (map vec->style (flatten-children
                                         styles))]
            (reduce merge-styles styles))))

;; Quering/applying

(defn select-style-with-type [style type]
  (if type
    (get (:types style) type nil)
    style))

(defn select-style-with-id [style id]
  (get-in style [:ids id]))

(defn select-style-with-class [style class]
  (get-in style [:classes class]))

(defn select-style-with-classes [style classes]
  (reduce merge-style-map
          (map #(select-style-with-class style %)
               classes)))

(defn select-style [style selector]
  (let [{:keys [type id classes]}
        (into-selector selector)
        type-style (select-style-with-type
                     style type)
        id-style (select-style-with-id
                   style id)
        class-style (select-style-with-classes
                      style classes)
        combined (merge-style-map
                   type-style id-style class-style)]
    combined))

(defn get-sub-style [style selector]
  (let [s (select-style style selector)]
    (merge-styles style (:sub-style s))))

(defn apply-style-f [node style f]
  (let [{:keys [selector children options]} node
        style-options (:options (select-style style selector))
        options (merge style-options options)
        sub-style (get-sub-style style selector)]
    (when style-options
      (f node options))
    (doseq [c children]
      (apply-style-f c sub-style f))
    nil))
