(ns tutorial01
  (:use [clojure.pprint]))

; prepare vmfest/virtual box
(use '[pallet.configure :only [compute-service]])

; Returns a compute service object, used to perform actions on a cloud provider.
(def vmfest (compute-service "vmfest" nil nil))

(use '[pallet.compute.vmfest :only [add-image]])
; duplicates ... 
(add-image vmfest "../vmfest/resources/ubuntu-10-10-64bit-server.vdi" :model-name "tutorial1")

; (use '[pallet.compute :only [images]])
(require 'pallet.core 'pallet.compute 'pallet.configure)
(pprint (pallet.compute/images vmfest))

(def mynode 
	(pallet.core/node-spec :image {:image-id "tutorial1"}))
; (pprint mynode)

(def mygroup
    (pallet.core/group-spec "mygroup" :count 1 :node-spec mynode))
; (pprint mygroup)

; BOOT UP
; (pallet.core/converge mygroup :compute vmfest)

; CHECK THE IP OF THE NEW NODE
; (pallet.compute/nodes vmfest)

; TRY TO LOGIN
; ssh into the VM at this stage with credentials:
; user/superduper

; SHUTDOWN : reduce the count of the VMs to 0
; (pallet.core/converge (pallet.core/group-spec "mygroup" :count 0) :compute vmfest)