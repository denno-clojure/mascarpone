(ns vmfest.core)

(use 'vmfest.manager)
(use '[vmfest.virtualbox.image :only [setup-model]])

;; First we need to define a connection to our VirtualBox host
;; service.
(def my-server (server "http://localhost:18083"))

;; We need an image model to play with. This will set up a fairly up-to-date
;; Debian image.
;; This will also look for the assiociated lubuntu.meta file in the same folder.
(setup-model "resources/lubuntu.vdi" my-server)
;; {:image-file "/var/folders...}

;; if you have failures regarding already existing media
;; try to remove previous disks through the 

;; let's check that the image model has been installed
(models)
;; (:lubuntu) <-- you should see this

;; Time do create a VM instance. We'll call it my-vmfest-vm. This is
;; the name that will appear in VirtualBox's GUI.
(def my-machine (instance my-server "my-vmfest-vm" :lubuntu :micro))

;; Notice that once we have created a VM we don't need to reference
;; the server anymore
(start my-machine)

;; Get the IP address of the machine. At this point, you can SSH into
;; this machine with user/password: vmfest/vmfest
(get-ip my-machine)

;; You can pause and resume the VM.
(pause my-machine)
(resume my-machine)

;; Stopping the VM will send a signal to the OS to shutdown. This will
;; not the VM itself, just the OS run by the VM
(stop my-machine)

;; This will turn off the VM completely and immediately.
(power-down my-machine)

;; Once we are done with this VM, we can destroy it, which will remove
;; any trace of it's existence. Your data will be lost, but not the
;; original image this VM was booted off.
(destroy my-machine)

;; for debugging, the following gives you a nice maps of metadata
(load-models)