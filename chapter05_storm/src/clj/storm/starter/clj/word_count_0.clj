(ns storm.starter.clj.word-count-0
  (:import [backtype.storm StormSubmitter LocalCluster])
  (:use [backtype.storm clojure config]))

(defspout sentence-spout ["sentence"]
  [conf context collector]
  (let [sentences ["a little brown dog"
                   "the man petted the dog"
                   "four score and seven years ago"
                   "an apple a day keeps the doctor away"]]
    (spout
     (nextTuple []
       (Thread/sleep 100)
       (emit-spout! collector [(rand-nth sentences)])         
       )
     (ack [id]))))

(defbolt split-sentence ["word"] [tuple collector]
  (let [words (.split (.getString tuple 0) " ")]
    (doseq [w words]
      (emit-bolt! collector [w] :anchor tuple))
    (ack! collector tuple)
    ))

(defn mk-topology []
  (topology
   {"1" (spout-spec sentence-spout)
    }
   {"2" (bolt-spec {"1" :shuffle} split-sentence)
   }))

(defn run-local []
  (let [cluster (LocalCluster.)]
    (.submitTopology cluster "word-count" {TOPOLOGY-WORKERS 3 TOPOLOGY-DEBUG true} (mk-topology))
    (Thread/sleep 10000)
    (.shutdown cluster)
    ))

(defn -main []
   (run-local))