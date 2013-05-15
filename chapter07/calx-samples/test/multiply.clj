(use 'calx)

(def gpu_source
  "__kernel void square (
       __global const float *a,
       __global float *b) {
    int gid = get_global_id(0);
    b[gid] = a[gid] * a[gid];
  }")

(with-cl
  (with-program (compile-program gpu_source)
    (let [a (wrap [1 2 3] :float32-le)
          b (mimic a)]
      (enqueue-kernel :square 3 a b)
      (enqueue-read b))))
; #<Ref to native buffer: (1.0 4.0 9.0)>