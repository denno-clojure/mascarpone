(ns impatient.stocks
  (:use [cascalog.api]
        [cascalog.more-taps :only (hfs-delimited)])
  (:gen-class))

(defn stock-tap[path-to]
  (hfs-delimited path-to
     :delimiter ","
     :outfields ["?exchange" "?stock-sym" "?date" "?open" "?high" "?low" "?close" "?volume" "?adj"]
     :classes [String String String Float Float Float Float Integer Float]
     :skip-header? true))

(defn -main [in out & args]
  (?<- 
   (hfs-delimited out)
   [?open]
   ((select-fields (stock-tap in) ["?stock-sym" "?open"]) :> ?stock-sym ?open)))