(use '[overtone.live]
     '[overtone.synth.stringed])

;; ======================================================================
;; ac/dc's highway to hell intro. turn it up!

(def g (guitar))

(defn ddd0 []
  (let [t (now) dt 250]
    (guitar-strum g [-1 0 2 2 2 -1] :down 0.01 (+ t (* 0 dt)))
    (guitar-strum g [-1 0 2 2 2 -1] :up 0.01 (+ t (* 1 dt)))
    (guitar-strum g [-1 0 2 2 2 -1] :down 0.01 (+ t (* 2 dt) 50))
    (guitar-strum g [-1 -1 -1 -1 -1 -1] :down 0.01 (+ t (* 3.5 dt)))))
(defn ddd1 []
  (let [t (now) dt 250]
    (guitar-strum g [ 2 -1 0 2 3 -1] :down 0.01 (+ t (* 0 dt)))
    (guitar-strum g [ 2 -1 0 2 3 -1] :up 0.01 (+ t (* 1 dt)))
    (guitar-strum g [ 3 -1 0 0 3 -1] :down 0.01 (+ t (* 2 dt) 50))
    (guitar-strum g [-1 -1 -1 -1 -1 -1] :down 0.01 (+ t (* 3.5 dt)))))
(defn ddd2 []
  (let [t (now) dt 250]
    (guitar-strum g [ 2 -1 0 2 3 -1] :down 0.01 (+ t (* 0 dt)))
    (guitar-strum g [-1 -1 -1 -1 -1 -1] :down 0.01 (+ t (* 1.5 dt)))
    (guitar-strum g [-1 0 2 2 2 -1] :down 0.01 (+ t (* 2 dt)))
    (guitar-strum g [-1 0 2 2 2 -1] :up 0.01 (+ t (* 3 dt)))
    (guitar-strum g [-1 -1 -1 -1 -1 -1] :down 0.01 (+ t (* 4.5 dt)))))

;; give us a good, crunchy sound
(ctl g :pre-amp 5.0 :distort 0.96
     :lp-freq 5000 :lp-rq 0.25
     :rvb-mix 0.5 :rvb-room 0.7 :rvb-damp 0.4)

;; play once
(ddd0) 
;; repeat 3 times
(ddd1) 
;; play once
(ddd2) 