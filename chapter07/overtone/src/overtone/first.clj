(use 'overtone.live)

(def kick (sample (freesound-path 2086)))

(def one-twenty-bpm (metronome 120))

(defn looper [nome sound]    
    (let [beat (nome)]
        (at (nome beat) (sound))
        (apply-at (nome (inc beat)) looper nome sound [])))

(looper one-twenty-bpm kick)
(stop)
