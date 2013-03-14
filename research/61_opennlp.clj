;(use 'clojure.pprint) ; just for this documentation

(use 'opennlp.nlp)
(use 'opennlp.treebank) ; treebank chunking, parsing and linking lives here

(def get-sentences (make-sentence-detector "models/en-sent.bin"))
(def tokenize (make-tokenizer "models/en-token.bin"))
(def detokenize (make-detokenizer "models/english-detokenizer.xml"))
(def pos-tag (make-pos-tagger "models/en-pos-maxent.bin"))
(def name-find (make-name-finder "models/namefind/en-ner-person.bin"))
(def chunker (make-treebank-chunker "models/en-chunker.bin"))

(pprint (chunker (pos-tag (tokenize "The override system is meant to deactivate the accelerator when the brake pedal is pressed."))))

(use 'opennlp.tools.filters)
; only nouns
(pprint (nouns (pos-tag (tokenize "Mr. Smith gave a car to his son on Friday."))))
; only verbs
(pprint (verbs (pos-tag (tokenize "Mr. Smith gave a car to his son on Friday."))))