(require '[clj-ldap.client :as ldap])

; port for apacheds
(def ldap-server (ldap/connect {:host "0.0.0.0:10389"}))

; add an entry
(ldap/add ldap-server "cn=nico,dc=example,dc=com"
               {:objectClass #{"top" "person"}
                :cn "nico"
                :description "His dudeness"
                :telephoneNumber ["1919191910" "4323324566"]
                :sn "dude2"})
; => {:code 0, :name "success"}

; query an entry
(ldap/get ldap-server "cn=nico,dc=example,dc=com")
; => {:objectClass #{"top" "person"}, :dn "cn=nico,dc=example,dc=com", :sn "dude2", :cn "nico", :telephoneNumber ["1919191910" "4323324566"], :description "His dudeness"}


; modify an entry 
 (ldap/modify ldap-server "cn=nico,dc=example,dc=com"
              {:add {:telephoneNumber "232546265"}})
; => {:code 0, :name "success"}

; delete all phone number entry
 (ldap/modify ldap-server "cn=nico,dc=example,dc=com"
              {:delete {:telephoneNumber :all}})
; => {:code 0, :name "success"}

; delete 
(ldap/delete ldap-server "cn=nico,dc=example,dc=com")
; => {:code 0, :name "success"}