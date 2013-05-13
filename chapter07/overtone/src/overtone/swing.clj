(use 'overtone.live)

; define a metronome at a given tempo, expressed in beats per minute.
(def metro (metronome 100))

(definst c-hat [amp 0.8 t 0.04]
  (let [env (env-gen (perc 0.001 t) 1 1 0 1 FREE)
        noise (white-noise)
        sqr (* (env-gen (perc 0.01 0.04)) (pulse 880 0.2))
        filt (bpf (+ sqr noise) 9000 0.5)]
    (* amp env filt)))

(definst o-hat [amp 0.8 t 0.5]
  (let [env (env-gen (perc 0.001 t) 1 1 0 1 FREE)
        noise (white-noise)
        sqr (* (env-gen (perc 0.01 0.04)) (pulse 880 0.2))
        filt (bpf (+ sqr noise) 9000 0.5)]
    (* amp env filt)))

(defn swinger [beat]
  (at (metro beat) (o-hat))
  (at (metro (inc beat)) (c-hat))
  (at (metro (+ 1.65 beat)) (c-hat))
  (apply-at (metro (+ 2 beat)) #'swinger (+ 2 beat) []))

(swinger (metro))