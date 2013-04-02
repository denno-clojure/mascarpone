; some imports
(require 
	'[clojurewerkz.quartzite.scheduler :as qs]
	'[clojurewerkz.quartzite.triggers :as t]
	'[clojurewerkz.quartzite.jobs :as j])
(use 
	'[clojurewerkz.quartzite.jobs 
		:only [defjob]]
	'[clojurewerkz.quartzite.schedule.simple 
		:only [schedule with-repeat-count with-interval-in-milliseconds]])

; init and start the system
(qs/initialize)
(qs/start)

; define the job
(defjob user.WeatherJob
	[ctx]
	(println "Beautiful weather today !"))

(def job 
	(j/build
	 (j/of-type WeatherJob)
	 (j/with-identity (j/key "jobs.1"))))

; define the trigger
(def trigger 
	(t/build
	 (t/with-identity (t/key "triggers.1"))
	 (t/start-now)
	 (t/with-schedule 
	 	(schedule
		 (with-repeat-count 10)
		 (with-interval-in-milliseconds 200)))))

; trigger the job
(qs/schedule job trigger)

; wait in the foreground
(Thread/sleep 2000)

; pause the job
(def job-key ((bean job) :key))
(qs/pause-job job-key)

; stop the scheduling system
(qs/stop)