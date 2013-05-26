(use '(clj3D math fl fenvs viewer) :reload)

;;Display a 1x1x1 cube
(view (cube 1))

;;view is a multimethod inherited from Incanter,
;;you can visualize data structures as well
(view (matrix [[0 1 2] [3 4 5]]))

;;You can use almost all Plasm's functions
;;Apply to all
(aa #(* %1 %1) [1 2 3])

;;Almost all functions are currified and high order
((aa #(* %1 %1)) [1 2 3])

;;Creates a green torus
(def green-torus (color :green (torus 0.5 1.0)))
(view green-torus)

;;Creates a red torus rotated by PI/2 on X axes and translated on X by -1.0
(def red-torus (struct2 (t 1 -1.0) (r 1 (/ PI 2.0)) (color :red) (torus 0.5 1.0)))

(view (struct2 green-torus red-torus))