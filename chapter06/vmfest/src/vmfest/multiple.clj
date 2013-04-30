(ns vmfest.multiple)

(use 'vmfest.manager)
(use '[vmfest.virtualbox.image :only [setup-model]])


;;; MULTIPLE INSTANCES

;; Now we are going to create multiple instances of the same image.
;; First we need some names for each instance. names will do just
;; that, e.g.: (names 3) -> ("vmfest-0" "vmfest-1" "vmfest-2").
(defn
  names [n]
  (map #(format "vmfest-%s" %) (range n)))

;; This function will create a debian instance based on the image
;; downloaded above
(defn deb-instance [server name]
  (instance server name :lubuntu :micro))

;; Let's create a few images. Notice that in this case we're creating
;; 5. Each machine takes roughly 0.5GB of RAM, so change the number to
;; match your available RAM.
(def my-machines (pmap #(deb-instance my-server %)
                       (names 5)))

;; From here we can start, power-down, and destroy all the VMs in parallel.
(pmap start my-machines)
(pmap power-down my-machines)
(pmap destroy my-machines)