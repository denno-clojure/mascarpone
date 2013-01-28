(require '[shake.core :as sh])      

(alter-var-root (var sh/*print-output*) (fn[_] true))

;; any shell command ...
(sh/uname -a)

;; using clojure variables (vars, local bindings) in shake
(let [home "/home/sunng87"]
  (sh/ls -l $home))

;; using clojure forms in shake
(sh/curl $(format "https://github.com/%s" "sunng87"))

; remove printing
(alter-var-root (var sh/*print-output*) (fn[_] false))
; import io classes
(use 'clojure.java.io)
;; keep output of `uname -a`
(def un (slurp (input-stream (sh/uname -a))))