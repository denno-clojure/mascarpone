(ns mixed-datatype
  (:use [calx])
  (:use
    [gloss.core]
    [clojure.test]))

(def gpu_source
  "struct __attribute__ ((packed)) mixed {
     short val;
     char step;
   };

   __kernel void invert (
       __global const struct mixed *a,
       __global struct mixed *b) {
    int gid = get_global_id(0);
    struct mixed src = a[gid];
    struct mixed m;
    m.val = src.val + src.step;
    m.step = src.step;
    b[gid] = m;
  }")

(def frame [:int16-le :byte])

 (with-cl
		(with-program (compile-program gpu_source)
		  (let [a (wrap [[0 2] [0 5]] frame)
			b (mimic a)]
		    (enqueue-read
		      (loop [i 0, a a, b b]
			(if (< 1000 i)
			  a
			  (do
			    (enqueue-kernel :invert 2 a b)
			    (recur (inc i) b a))))))))
; #<Ref to native buffer: ([2002 2] [5005 5])>			    
; @value
