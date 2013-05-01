(ns music.midi)
(import 'javax.sound.midi.MidiSystem)

(declare key-number)

(defrecord Note [pitch octave])

(defrecord Chord [notes duration])

(defprotocol MidiEvent
    (play [this tempo midi-channel]))

(extend-type Chord 
    MidiEvent 
    (play [this tempo midi-channel]
        (let [velocity (or (:velocity this) 64)
            ]
            (doseq [aNote (map #(key-number %) (:notes this))] 
                (.noteOn midi-channel aNote velocity)
            ) 
            (Thread/sleep (:duration this))
            )))

(defn perform [chord & {:keys [tempo] :or {tempo 120}}]
    (with-open [synth (doto (MidiSystem/getSynthesizer) .open)]
        (let [channel (aget (.getChannels synth) 0)]
                (play chord tempo channel))))

(defn key-number [note]
    (let [scale {:C 0,  :C# 1, :Db 1,  :D 2,
                 :D# 3, :Eb 3, :E 4,   :F 5,
                 :F# 6, :Gb 6, :G 7,   :G# 8,
                 :Ab 8, :A 9,  :A# 10, :Bb 10,
                 :B 11}]
      (+ (* 12 (inc (:octave note)))
         (scale (:pitch note)))))

(defn make-chord [notes]
    (->Chord (map #(->Note % 4) notes) 2000)
)
