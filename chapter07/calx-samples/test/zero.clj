(use 'calx)

(println "Plateforms\n" 
  (available-platforms))
(println "Devices\n"
  (available-devices))
(println "CPUs\n"    
  (available-cpu-devices))
(println "GPUs\n"
  (available-gpu-devices))
(println "Select Device\n"
  (best-device))
(println "Version\n"
  (version))