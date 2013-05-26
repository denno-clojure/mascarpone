goog.provide('example.test');
goog.require('cljs.core');
goog.require('example.test.hello');
example.test.success = 0;
example.test.run = (function run(){
console.log("Example test started.");
example.test.hello.run.call(null);
return example.test.success;
});
goog.exportSymbol('example.test.run', example.test.run);
