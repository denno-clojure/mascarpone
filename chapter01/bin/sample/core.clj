(ns sample.core 
	:gen-class)

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

(defn hello 
  [x]
  (println "Hello" x "!"))

(defn helloworld
  [name]
  (println "Hello, " name))

(defn -main [& args]
	(helloworld args))