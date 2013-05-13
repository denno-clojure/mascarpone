(ns speech.speak)

(use '[speech-synthesis.say :as say])
(say/say "Bonjour !")

(say/say "Give me some wine please.")