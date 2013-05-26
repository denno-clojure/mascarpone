(ns orbit.util
  (:require (clojure
              [edn :as edn]
              [string :as cstr])))

(defmacro arg-err [& err]
  `(throw (IllegalArgumentException.
           ^String (apply println-str (vector ~@err)))))

(defmacro req-err [option]
  `(arg-err ~option "option required!"))

(defmacro convert-err [obj]
  `(arg-err "cannot be converted:" ~obj))

;; =====

(defn dash-to-camel [string]
  (let [string (name string)
        words (.split ^String string "-")
        capitalized (map cstr/capitalize (rest words))]
    (apply str (first words) capitalized)))

;; =====
;; Color
;; =====
(defn float-color [coll]
  (map #(float (/ % 255)) coll))

(defn rgba-color [string]
  (let [vs (vec (map (comp int edn/read-string)
                     (re-seq #"\d+" string)))
        vs (float-color vs)]
    (case (count vs)
      3 (conj vs 1.0)
      4 vs
      (convert-err string))))

(defn hex-color [string]
  (let [string (subs string 1)
        string (case (count string)
                 3 (str string "f")
                 4 string
                 6 (str string "ff")
                 8 string
                 (arg-err
                   string
                   "is not a valid hex color."))
        vs-raw (if (== (count string) 4)
                 (map #(str % %) string)
                 (map #(apply str %)
                      (partition 2 string)))
        vs (map #(edn/read-string
                   (str "0x" %))
                vs-raw)]
    (float-color vs)))
    
(defn string->color [string]
  (cond
    (re-matches #"rgba?\([ \d]+,[ \d]+,[ \d]+\)"
                string)
     (rgba-color string)
    (re-matches #"#[\dA-Fa-f]{3,4}" string)
     (hex-color string)
    ))

(defn clamp-color [coll]
  (map #(max 0.0 (min 1.0 %)) coll))

(defn color
  [obj]
  (clamp-color
    (cond
      (string? obj) (string->color obj)
      (number? obj) (concat (repeat 3 obj)
                            [1.0])
      (sequential? obj)
      (case (count obj)
        3 (concat obj [1.0])
        4 obj
        (arg-err
          obj "is not of length 3 or 4."))
      :else (convert-err obj))))

(defn color->hex [c]
  (->> c
       color
       (map #(int (* % 255)))
       (map #(format "%02x" %))
       (apply str)
       (str "#")))
