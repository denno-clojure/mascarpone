goog.provide('example.crossover.shared');
goog.require('cljs.core');
example.crossover.shared.make_example_text = (function make_example_text(){
return [cljs.core.str("\u304A\u306F\u3088\u3046 "),cljs.core.str("from the "),cljs.core.str("shared "),cljs.core.str("code")].join('');
});
