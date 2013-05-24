(ns org.stuff.clojuroid.main)

; import the neko UI library namespaces if needed
; (use 'neko.resource 'neko.activity 'neko.notify 'neko.ui 'neko.threading 'neko.application)

(import 'android.net.Uri android.content.Intent)

; prepare the intent
(defn call-number [number]
	(let [intent (Intent. Intent/ACTION_DIAL)]
		(.setData intent (Uri/parse (str "tel:" number)))
		(.startActivity a intent)))

; call domino pizza
(call-number "03-3583-2266")