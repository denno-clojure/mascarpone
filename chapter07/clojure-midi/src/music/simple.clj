(ns music.simple
  (:use music.midi music.chord music.inverter))

(def AMajor [:A :B :C# :D :E :F# :G#])
; AMajor

(music.chord/triad AMajor :A)
; (:A :C :E)

(music.chord/seventh_chord AMajor :A)
; (:A :C :E :G)

(make-chord 
	(music.chord/triad AMajor :A))
; #music.midi.Chord{
;	:notes (#music.midi.Note{:pitch :A, :octave 4} 
;	        #music.midi.Note{:pitch :C#, :octave 4} 
;	        #music.midi.Note{:pitch :E, :octave 4}), 
;	:duration 2000}

(perform 
	(make-chord 
	  (music.chord/triad AMajor :A)))
; ♩