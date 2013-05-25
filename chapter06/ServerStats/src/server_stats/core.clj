(ns ^{:doc "Runs a set of defined commands in server-stats.cfg, and optionally will trigger an alert if a value meets a defined condition"
      :author "Chris McBride"} 
  server-stats.core
  (:require  [parallel-ssh.core :as pssh]
             [server-stats.config :as cfg])
  (:use [clojure.string :only (split split-lines replace-first join)]
        [clojure.tools.cli :only (cli)])
  (:gen-class))

(def ^{:doc "Map of parsers functions to convert strings from command out to clojure values"} ;TODO: put this in config file?
  value-type-parsers
    {:percent #(read-string (re-find #"\d+\.?\d*" %))
     :bool #(if (= % "true") true false)
     :number #(read-string (re-find #"\d+\.?\d*" %))})

(defn check-and-trigger-alerts 
  [^parallel_ssh.core.CommandResult cmdresult 
   ^server_stats.config.Alert alert]
      (let [{:keys [out server]} cmdresult
            make-alert-trigger-checker (fn [condition-tuple]
                                          ;Given a list/tuple containing a boolean operator (eg. <) and a threshold value, make and return a function that takes a single argument
                                          (fn [x#] (eval `(~(first condition-tuple) ~x# ~@(rest condition-tuple)))))
            out-lines (split-lines out)
            alert-msg (:msg alert)
            does-value-trigger-alert? (make-alert-trigger-checker (:trigger alert))
            string-to-value ((keyword (:value-type alert)) value-type-parsers)
            is-column-formatted-output? (not (nil? (:column alert)))
            extract-value-from-line (if is-column-formatted-output?
                                      (let [header-row (first out-lines)
                                            col-pos (.indexOf (split header-row #"\s+") (:column alert))]
                                              (fn [row] (nth (split row #"\s+") col-pos)))
                                      identity)
            data-rows (if is-column-formatted-output? (rest out-lines) out-lines)
            rows-that-triggered-an-alert (filter (comp does-value-trigger-alert? string-to-value extract-value-from-line) data-rows)
            alert-handler-functions (map #((keyword %) @cfg/alert-handlers) (:handlers alert))]
                (doseq [row rows-that-triggered-an-alert,
                        handler alert-handler-functions] 
                  (handler alert-msg server row))))

(defn execute-command
  "For every server, run the command, and return the result records"
  [cmd-name]
    (let [cmd-bash-string ((keyword cmd-name) @cfg/cmds)
          servers-to-run-cmd-on ((keyword cmd-name) @cfg/cmd-servers)
          username @cfg/ssh-username
          cmd-timeout-in-ms 90000]
      (pssh/run-commands cmd-bash-string servers-to-run-cmd-on username cmd-timeout-in-ms)))

(defn run-alerts
  [cmd-name commandResults]
    (let [alerts-map ((keyword cmd-name) @cfg/cmd-alerts)
          valid-server-responses (filter pssh/valid-server-response? commandResults)
          invalid-server-responses (filter (comp not pssh/valid-server-response?) commandResults)]
        (doseq [alert alerts-map resp valid-server-responses] (check-and-trigger-alerts resp alert))
        (doseq [resp invalid-server-responses] (@cfg/cmd-failure-handler (:server resp)))))

(defn- parse-cli-args
  [args]
    (cli args
      ["-h" "--help" "Show help" :default false :flag true]
      ["-a" "--[no-]alerts" "Send email alerts" :default false]))

(defn get-valid-commands-string 
  "Produce the list of commands and docs for the man page"
  []
  (str "\nValid commands are:\n\n"
    (join "\n"
      (map #(format "%-30.30s  %-30.90s" (name (key %)) (val %)) @cfg/cmd-docs))))

(defn print-usage-page-and-die
  [usage-str]
    (do
      (print (replace-first usage-str "Usage:" "Usage: [switches] command-name"))
      (print (get-valid-commands-string))
      (newline)
      (flush)
      (System/exit 1)))

(defn -main [& args]
  (let [[{:keys [help alerts]} [command-name & _] usage-message] (parse-cli-args args)
        valid-command? #(contains? @cfg/cmds (keyword %))] 
    (if (or (= help true) (not (valid-command? command-name)))
      (print-usage-page-and-die usage-message)
      (do
        (let [commandResults (execute-command command-name)]
          (println (pssh/format-outputs commandResults))
          (println alerts)
          (when alerts
            (run-alerts command-name commandResults)))))))

