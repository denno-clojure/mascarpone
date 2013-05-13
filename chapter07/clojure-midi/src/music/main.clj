(ns music.main
(:use music.midi music.chord music.inverter))

(defn -main []
    (let [progression (map #(music.chord/triad music.chord/C %) (take 7 (music.chord/intervals music.chord/C 3)))]
      (doseq [toplay (map #(make-chord %) progression)] 
        (perform toplay)
    )))

(def Edorian [:E :F :G :A :B :C :D])
(def AMajor [:A :B :C# :D :E :F# :G#])
(music.chord/seventh_chord Edorian :F)