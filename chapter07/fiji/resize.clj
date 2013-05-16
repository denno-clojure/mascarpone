(import '(ij IJ ImagePlus))

(def imp-1 (IJ/openImage "PacManukulele.jpg"))
 
(def imp-2 (ImagePlus. "A copy with extra empty space"
                       (.. imp-1 getProcessor duplicate (resize 768 768))))

(.show imp-2)