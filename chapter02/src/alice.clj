(require '[clj-crypto.core :as c])
(import '[java.security KeyPair])

(def key-pair (c/generate-key-pair))
(def data "secret text")

(def encrypted-text (c/encrypt key-pair data))

(def decrypted-text (c/decrypt key-pair encrypted-text))

(println decrypted-text)