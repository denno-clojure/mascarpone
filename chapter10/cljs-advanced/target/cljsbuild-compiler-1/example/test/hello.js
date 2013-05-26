goog.provide('example.test.hello');
goog.require('cljs.core');
goog.require('example.hello');
goog.require('example.hello');
example.test.hello.run = (function run(){
if(cljs.core._EQ_.call(null,example.hello.add_some_numbers.call(null,2,2),4))
{} else
{throw (new Error([cljs.core.str("Assert failed: "),cljs.core.str(cljs.core.pr_str.call(null,cljs.core.with_meta(cljs.core.list(new cljs.core.Symbol(null,"=","=",-1640531466,null),cljs.core.with_meta(cljs.core.list(new cljs.core.Symbol(null,"add-some-numbers","add-some-numbers",-1452268074,null),2,2),cljs.core.hash_map("\uFDD0:line",5,"\uFDD0:column",14)),4),cljs.core.hash_map("\uFDD0:line",5,"\uFDD0:column",11))))].join('')));
}
if(cljs.core._EQ_.call(null,example.hello.add_some_numbers.call(null,1,2,3),6))
{} else
{throw (new Error([cljs.core.str("Assert failed: "),cljs.core.str(cljs.core.pr_str.call(null,cljs.core.with_meta(cljs.core.list(new cljs.core.Symbol(null,"=","=",-1640531466,null),cljs.core.with_meta(cljs.core.list(new cljs.core.Symbol(null,"add-some-numbers","add-some-numbers",-1452268074,null),1,2,3),cljs.core.hash_map("\uFDD0:line",6,"\uFDD0:column",14)),6),cljs.core.hash_map("\uFDD0:line",6,"\uFDD0:column",11))))].join('')));
}
if(cljs.core._EQ_.call(null,example.hello.add_some_numbers.call(null,4,5,6),15))
{return null;
} else
{throw (new Error([cljs.core.str("Assert failed: "),cljs.core.str(cljs.core.pr_str.call(null,cljs.core.with_meta(cljs.core.list(new cljs.core.Symbol(null,"=","=",-1640531466,null),cljs.core.with_meta(cljs.core.list(new cljs.core.Symbol(null,"add-some-numbers","add-some-numbers",-1452268074,null),4,5,6),cljs.core.hash_map("\uFDD0:line",7,"\uFDD0:column",14)),15),cljs.core.hash_map("\uFDD0:line",7,"\uFDD0:column",11))))].join('')));
}
});
