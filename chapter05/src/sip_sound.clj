(import '(javax.sound.sampled AudioSystem AudioFormat$Encoding))

(import 'javafx.scene.media.Media)
(import 'javafx.scene.media.MediaPlayer)

; String bip = "resources/tryme.wav";
(.play (MediaPlayer. (Media. "http://www-mmsp.ece.mcgill.ca/documents/AudioFormats/WAVE/Samples/AFsp/M1F1-Alaw-AFsp.wav")))
; Media hit = new Media(bip);
; MediaPlayer mediaPlayer = new MediaPlayer(hit);
; mediaPlayer.play();

; (let [mp3-file (java.io.File. "resources/tryme.wav")
; 	audio-in (AudioSystem/getAudioInputStream mp3-file)
; 	audio-decoded-in (AudioSystem/getAudioInputStream AudioFormat$Encoding/PCM_SIGNED audio-in)
; 	buffer (make-array Byte/TYPE 1024)]
; 	(loop []
; 		(let [size (.read audio-decoded-in buffer)]
; 			(when (> size 0)
	           
; 	        (recur)))))