(ns chapter03w.core
	(:gen-class))

(defn foo
  "I don't do a whole lot."
  [x]
  (println "Hello, " x "!"))

(defn -main[& args]
	(if args
		(foo (first args))
		(foo "World")))