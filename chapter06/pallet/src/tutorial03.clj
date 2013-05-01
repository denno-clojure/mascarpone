(use '[pallet.action.package :only [package]]
     '[pallet.phase :only [phase-fn]])

(pallet.core/converge
  (pallet.core/group-spec "mygroup"
   :count 1
   :node-spec mynode
   :phases {
   	:configure (phase-fn (package "curl"))}
   )
  :compute vmfest)