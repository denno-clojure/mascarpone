(ns monitor.core
	(:use [clj.monitor.core]
		[control.core]
		[clj.monitor.tasks]))

(defcluster mysql
	:clients [{:user "nicolas" :host "jp-9"}])


(defmonitor mysql-monitor
         ;;Tasks to monitor mysql,we just ping mysql and make sure that average load in 5 minutes is less than 3
         :tasks [(ping-mysql "root" "password")
         (system-load :5 3)]
         ;;Mysql clusters for monitoring
         :clusters [:mysql])

(start-monitors
	:cron "1 * * * * ?"
	:alerts [(mail :from "alert@app.com" :to "hellonico@gmail.com")]
	:monitors [mysql-monitor])