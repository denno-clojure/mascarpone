goog.provide('example.hello');
goog.require('cljs.core');
goog.require('example.crossover.shared');
example.hello.say_hello = (function say_hello(){
return alert(example.crossover.shared.make_example_text.call(null));
});
goog.exportSymbol('example.hello.say_hello', example.hello.say_hello);
/**
* @param {...*} var_args
*/
example.hello.add_some_numbers = (function() { 
var add_some_numbers__delegate = function (numbers){
return cljs.core.apply.call(null,cljs.core._PLUS_,numbers);
};
var add_some_numbers = function (var_args){
var numbers = null;
if (arguments.length > 0) {
  numbers = cljs.core.array_seq(Array.prototype.slice.call(arguments, 0),0);
} 
return add_some_numbers__delegate.call(this, numbers);
};
add_some_numbers.cljs$lang$maxFixedArity = 0;
add_some_numbers.cljs$lang$applyTo = (function (arglist__3466){
var numbers = cljs.core.seq(arglist__3466);
return add_some_numbers__delegate(numbers);
});
add_some_numbers.cljs$core$IFn$_invoke$arity$variadic = add_some_numbers__delegate;
return add_some_numbers;
})()
;
