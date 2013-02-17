; include this for any programming with clisk
(use '[clisk live])

;; Create a Voronoi map based on a mathematical function
(def vblocks 
  (v* 5.0 
      (voronoi-function 
        `(Math/sqrt (- (* ~'y ~'y) (* ~'x ~'x))))))

;; Render an texture using the Voronoi map as a height-field
(show (render-lit 
        (seamless vplasma) 
        (v+ (v* 0.2 (seamless 0.2 (rotate 0.1 plasma))) 
            (v* 0.6 vblocks))))

