(ns org.stuff.clojuroid.main)

; import the neko UI library namespaces
(use 'neko.resource 'neko.activity 'neko.notify 'neko.ui 'neko.threading 'neko.application)

; en route for list layout
(use 'neko.ui.adapters)

; must be a ref
(def alphabet
   (atom ["alpha" "bravo" "charlie" "delta" "hello"]))

 (def adapter (ref-adapter
               (fn [] (make-ui [:text-view {}]))
               (fn [position view _ data]
                 (.setText ^android.widget.TextView view (str position ". " data)))
               alphabet
               identity
               ))

 ;; Somewhere in Activity.onCreate()
 (on-ui
     (set-content-view! a
       (make-ui 
        [:linear-layout {}
        [:list-view {:adapter adapter}]])))

; add a new element in the list
(swap! alphabet conj "new")