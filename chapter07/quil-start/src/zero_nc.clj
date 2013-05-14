(ns zero
  (:use quil.core))       

(defn setup []
  (smooth)
  (background 0))   

(defn draw []
  (stroke (random 255))
  (stroke-weight (random 10))    
  (fill (random 255)) 
  ; (fill-int (color (random 255) (random 255) (random 255)) (random 255))

  (let [diam (random 100)           
        x    (random (width))       
        y    (random (height))]     
    (ellipse x y diam diam)))       

 (defsketch example                 
  :title "Oh so many grey circles"                     
  :draw draw  
  :setup setup                      
  :size [512 380])                  