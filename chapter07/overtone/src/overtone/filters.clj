(use 'overtone.live)

(demo 10 (lpf (saw 100) (mouse-x 40 5000 EXP)))
;; low-pass; move the mouse left and right to change the threshold frequency

(demo 10 (hpf (saw 100) (mouse-x 40 5000 EXP)))
;; high-pass; move the mouse left and right to change the threshold frequency

(demo 30 (bpf (saw 100) (mouse-x 40 5000 EXP) (mouse-y 0.01 1 LIN)))
;; band-pass; move mouse left/right to change threshold frequency; up/down to change bandwidth (top is narrowest)


;; here we generate a pulse of white noise, and pass it through a pluck filter
;; with a delay based on the given frequency
(let [freq 220]
   (demo (pluck (* (white-noise) (env-gen (perc 0.001 2) :action FREE)) 1 3 (/ 1 freq))))