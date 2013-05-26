goog.provide('example.repl');
goog.require('cljs.core');
goog.require('clojure.browser.repl');
example.repl.connect = (function connect(){
return clojure.browser.repl.connect("http://localhost:9000/repl");
});
goog.exportSymbol('example.repl.connect', example.repl.connect);
