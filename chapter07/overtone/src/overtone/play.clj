(ns overtone.play)

(use 'overtone.live)

(definst foo [] (saw 220))
(foo)
(kill foo)

(definst bar [freq 220] (saw freq))
(bar 110)
(bar 220)

(definst quux [freq 440] 
	(* 0.3 (saw freq)))
(quux)
(ctl quux :freq 660)


(definst trem [freq 440 depth 10 rate 6 length 3]
    (* 0.3
       (line:kr 0 1 length FREE)
       (saw (+ freq (* depth (sin-osc:kr rate))))))

(trem)
(trem 200 60 0.8)
(trem 60 30 0.2)

(demo (sin-osc))