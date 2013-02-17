(use '[clisk live])

; mandelbrot
(show (viewport [-2 -1.5] [1 1.5]
  (fractal
    :while (v- 2 (length [x y]))
    :update (v+ c [(v- (v* x x) (v* y y)) (v* 2 x y)])
    :result (vplasma (v* 0.1 'i))
    :bailout-result black
    :max-iterations 1000)) 1024 1024)