(ns simple-addition
  (:use [calx])
  (:use [clojure.test]))

(def gpu_source
  "__kernel void vec_add (
       __global const float *a,
       __global const float *b,
       __global float *c) {
    int gid = get_global_id(0);
    c[gid] = a[gid] + b[gid];
  }")

(with-cl
		(with-program (compile-program gpu_source)
		  (let [
		    a (wrap [1 2 3] :float32-le)
			b (wrap [1 2 3] :float32-le)
			c (mimic a)]
		    (enqueue-kernel :vec-add 3 a b c)
		    (enqueue-read c))))
; #<Ref to native buffer: (2.0 4.0 6.0)>