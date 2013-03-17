(require 
    '[bouncer [core :as b] [validators :as v]])

(def person {:name "Leo"})

; fails, no age
(b/validate person
    :name v/required
    :age  v/required)
; =>
; [{:age ("age must be present")} {:name "Leo", :bouncer.core/errors {:age ("age must be present")}}]

; define the validator
(v/defvalidatorset name-validator
  :name v/required)

; use it
(b/validate 
    person 
    name-validator)
; => 
; [nil {:name "Leo"}]

; collections
(def person
    {:address
        {:street nil
         :country "Brazil"
         :postcode "invalid"
         :phone "foobar"}})

(b/validate person
    [:address :street]   v/required
    [:address :postcode] v/number
    [:address :phone] (v/matches #"^\d+$"))
; =>
;     [
;     {
;      :address {:phone ("phone must satisfy the given pattern"), 
;      :postcode ("postcode must be a number"), 
;      :street ("street must be present")}
;      } 
;      {:bouncer.core/errors {
;         :address 
;         {
;         :phone ("phone must satisfy the given pattern"), 
;         :postcode ("postcode must be a number"), 
;         :street ("street must be present")
;         }
;     }, 
;     :address {:country "Brazil", :postcode "invalid", :street nil, :phone "foobar"}}
; ]
