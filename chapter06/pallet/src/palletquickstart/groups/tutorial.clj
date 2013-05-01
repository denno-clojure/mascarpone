(ns tutorial
  (:use [clojure.pprint]))

; prepare vmfest/virtual box
(use '[pallet.configure :only [compute-service]])
(def vmfest (compute-service "vmfest" nil nil))
; also keeps the vmfest for future reference

(use '[pallet.compute.vmfest :only [add-image]])
; duplicates ... 
; (add-image vmfest "../vmfest/resources/lubuntu.vdi" :model-name "tutorial")

; (use '[pallet.compute :only [images]])
(pprint (pallet.compute/images vmfest))

(def mynode 
	(pallet.core/node-spec :image {:image-id "lubuntu"}))
(pprint mynode)

(def mygroup-up
    (pallet.core/group-spec "mygroup"  :count 1 :node-spec mynode))
(pprint mygroup-up)

; http://www.raynes.me/logs/irc.freenode.net/pallet/2012-09-09.txt

; WORKS !
; (pallet.core/converge mygroup-up :compute vmfest)

; (def mygroup-down
;     (pallet.core/group-spec "mygroup" :count 0 :node-spec mynode))
; (pallet.core/converge mygroup-down :compute vmfest)

(use '[pallet.crate.automated-admin-user :only [automated-admin-user]])
(def mygroup-2
    (pallet.core/group-spec "mygroup"  :count 1 :node-spec mynode :phases {:bootstrap automated-admin-user}))

(pallet.core/converge
  mygroup-2
  :compute vmfest)