(ns tutorial)

; prepare vmfest/virtual box
(use '[pallet.configure :only [compute-service]])
(def vmfest (compute-service "vmfest" nil nil))

(use '[pallet.compute.vmfest :only [add-image]])
(add-image vmfest "../vmfest/resources/lubuntu.vdi" :model-name "tutorial")

; now get ready to integrate with pallet
(require 'pallet.core 'pallet.compute 'pallet.configure)

(pprint (images vmfest))

; (pallet.core/group-spec "mygroup"
;   :count 1
;   :node-spec (pallet.core/node-spec
;               :image {:os-family :ubuntu :image-id "us-east-1/ami-3c994355"}))

(def mygroup
    (group-spec "mygroup" 
         :node-spec {:image {:image-id "lubuntu"}}))

(pallet.core/converge mygroup :compute (pallet.configure/compute-service :vmfest))
