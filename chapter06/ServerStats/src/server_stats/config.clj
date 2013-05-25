(ns ^{:doc "Reads and stores the values in server-stats.cfg"
      :author "Chris McBride"} 
  server-stats.config
  (:use [clojure.main :only (load-script)])
  (:gen-class))

(def config-file-name "config file has to be in the same folder" "server-stats.cfg")
(def alert-handlers 
  "Map of alert handler name to function. These functions can be used when an alert condition is met. 
   Each function takes 3 arguments: the alertmessage, server name, and output from the command that triggered the alert" 
    (atom {}))

(def ^{:argslist '(server-name)} cmd-failure-handler "This function will get called everytime a ssh command fails" (atom (fn [& _] _)))
(def cmds "Map of command name to actual bash string to be run" (ref {}))
(def cmd-docs "Map of command name to doc string for man page" (ref (sorted-map)))
(def cmd-alerts "Map of command name to alert condition to test" (ref {}))
(def cmd-servers "Map of command name to a list of server names to run it on" (ref {}))
(def server-groups "Map of server group name to vector of server names" (atom {}))
(def ssh-username "The username used for all cmds" (atom ""))

(defrecord Command [doc cmd alerts servers])
(defrecord Alert [column value-type msg trigger handlers])

(defmacro add-server-group
  "This macro is used in the config file to add server groups"
  [group-name server-str-vec]
    `(swap! server-groups assoc ~(keyword group-name) ~server-str-vec))

(defn set-ssh-username 
  [username]
    (reset! ssh-username username))

(defmacro add-alert-handler 
  "Config file function to set alert handler"
  [handler-name, handler-args & alert-handler-body]
    `(swap! alert-handlers assoc ~(keyword handler-name) (fn ~handler-args ~@alert-handler-body)))

(defmacro set-command-failure-handler
  "Config file function to set function that gets called on command failure"
  [handler-args & cmd-fail-handler-body]
    `(reset! cmd-failure-handler (fn ~handler-args ~@cmd-fail-handler-body)))

(defmacro add-cmd
  "This macro is used in the config file to add more commands"
  [cmd-name,
   ^Command {:keys [doc alerts cmd servers]}]
    `(dosync
      (ref-set cmds (assoc @cmds ~(keyword cmd-name) ~cmd))
      (ref-set cmd-servers (assoc @cmd-servers ~(keyword cmd-name) (flatten (map #((keyword %) @server-groups) (quote ~servers)))))
      (ref-set cmd-alerts (assoc @cmd-alerts ~(keyword cmd-name) (map #(map->Alert %) (quote ~alerts))))
      (ref-set cmd-docs (assoc @cmd-docs ~(keyword cmd-name) ~doc))))

(load-script config-file-name) ; TODO: This stackoverflow post is the proper way to do this, 
                               ; http://stackoverflow.com/questions/8627880/how-do-i-eval-a-clojure-data-structure-within-the-context-of-a-namespace
