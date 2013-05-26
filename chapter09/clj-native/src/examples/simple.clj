(ns examples.simple
  (:use [clj-native.direct :only [defclib loadlib typeof]]
        [clj-native.structs :only [byref byval]]
        [clj-native.callbacks :only [callback]]))

(defclib
  simple
  (:libname "simple")
  (:functions
   (add [int int] int)
   (returnsConstantString [] constchar*)))

(defn -main[]
  (loadlib simple)
  (println (returnsConstantString))
  (println (add 1 2)))