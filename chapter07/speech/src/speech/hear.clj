(use '[speech-recognition.hear :as hear])

(binding [
    *language* "ja" 
    *input-index* 2
    *sample-time* 2000] 
    (hear/hear))