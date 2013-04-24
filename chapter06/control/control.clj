(defcluster :default-cluster
  :clients [
    {:host "jp-1" :user "nicolas"}
  ])

(deftask :date "echo date on cluster"  []
  (ssh "date"))
