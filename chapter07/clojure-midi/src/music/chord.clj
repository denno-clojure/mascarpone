(ns music.chord
(:require 
    [clojure.math.combinatorics :as comb] 
    [music.inverter :as inverter ])
)
(def C [:C :D :E :F :G :A :B])

(defn chord 
    ([num_voices scale degree] 
        (let [degree (if (keyword? degree) (.indexOf scale degree) degree)] 
            (take num_voices (take-nth 2 (cycle (inverter/transposer scale degree))))
        )
    ) 
    ([scale degree] 
        ; default to 3 voiced chords if no number of voices is specified
        (chord 3 scale degree))

    ([scale] 
        (chord scale (first scale)))
    )

(defn intervals
    ([scale interval] 
    (if (<= interval 0)
        nil
        (let [interval_to_invert (dec interval)] 
            (lazy-seq (cons (first scale) (intervals (inverter/transposer scale interval_to_invert) interval)
    ))))))

(defn chord_scale [voices interval scale]
    (map 
        #(chord voices scale %) 
        (take (count scale) (intervals scale interval))))

; use partial function application to define a triad
(def triad (partial chord 3))
(def seventh_chord (partial chord 4))

