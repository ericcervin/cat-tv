;;based on Visualize Superformula
;;from Form+Code in Design, Art, and Architecture by Reas, etc.

(ns cat-tv.cattv-20170416
  (:require [quil.core :as q]
            [quil.middleware :as m]))


  
(defn super-formula-point [m n1 n2 n3 phi]
    (let [a 1.0
          b 1.0
          x 0.0
          y 0.0
    
          t1 (/ (q/cos (* m (/ phi 4))) a)
          t1 (q/abs t1)
          t1 (q/pow t1 n2)
          
          t2 (/ (q/sin (* m (/ phi 4))) b)
          t2 (q/abs t2)
          t2 (q/pow t2 n3)
               
          r (q/pow (+ t1 t2)(/ 1 n1))
              
          r (if (= r 0) 0 (/ 1 r)) 
          x (if (= r 0) 0 (* r (q/cos phi)))
          y (if (= r 0) 0 (* r (q/sin phi)))]
              
         {:x x :y y}))

(defn superformula [m n1 n2 n3]  
  (let [num-points 360
        phi (/ q/TWO-PI num-points)]
  
    (take 400 (map #(super-formula-point m n1 n2 n3 (* phi %)) (range (+ num-points 1))))))

(defn setup []
   (q/no-fill)
   (q/stroke 255)
   {:scalar 250
     :m 2
     :n1 18.0
     :n2 1.0
     :n3 1.0
     :step-list  (concat (range 64) (reverse (range 64)))})

(defn draw [state]
  (q/background 0)
  (q/push-matrix)
  (q/translate (/ (q/width) 2) (/ (q/height) 2))
  (let [steps (rem (rem (q/frame-count) 300) 128)
        newscalar (:scalar state)]
       (doseq [s (range (nth (:step-list state) steps) 0 -1)]
         ;;(println s)
         (q/begin-shape)
         (let [mm  (+ (:m state) s)
               nn1 (+ (:n1 state) s)
               nn2 (+ (:n2 state) s)
               nn3 (+ (:n3 state) s)
               newscalar (* newscalar (q/pow 0.98 s))
               sscalar newscalar
               points (superformula mm nn1 nn2 nn3)]
             
              (q/curve-vertex  (* (:x (last points)) sscalar) (* (:y (last points)) sscalar))
              (doseq [i (range (count points))]
               (q/curve-vertex (* (:x (nth points i)) sscalar) (* (:y (nth points i)) sscalar)))
             (q/curve-vertex (* (:x (nth points 0)) sscalar) (* (:y (nth points 0)) sscalar)))
         (q/end-shape)))
  (q/pop-matrix))
        



(q/defsketch practice
  :size [300 300]
  :setup setup
  ;;:update update-state
  :renderer :p3d
  :draw draw
  :features [:keep-on-top]
  :middleware [m/fun-mode])
