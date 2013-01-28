

(use 'clj-ssh.cli)
; most of this code will not work out of the box, you need a real ssh server to test against.

; if your ssh key is different than the usual, start by adding it to the ssh agent
(add-identity :private-key-path "/Users/niko/.ssh/id_rsa")

; in its simplest form, execute a remote command
(ssh "my-host" "ls")
; => {:exit 0 :out "file1\nfile2\n" :err "")

; if you need to change the user name
(ssh "my-host" "ls" :username "remote-user")
; => {:exit 0 :out "file1\nfile2\n" :err "")

; with more options
(ssh "bonjourclojure.net" "ls -al" :username "macniko" :port 8022) 

; here is how to use SFTP
(sftp "my-host" :put "/from/this/path" "to/this/path")

; tunneling from port 80 to 8080
 (let [agent (ssh-agent {})]
      (let [session (session agent "localhost" {:strict-host-key-checking :no})]
        (with-connection session
          (with-local-port-forward [session 8080 80]
            (comment do something with port 8080 here)))))