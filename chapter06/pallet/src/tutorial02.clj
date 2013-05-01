(use '[pallet.crate.automated-admin-user :only [automated-admin-user]])

(pallet.core/converge
  (pallet.core/group-spec "mygroup"
   :count 1
   :node-spec mynode
   :phases {
   	:bootstrap automated-admin-user 
   )
  :compute vmfest)