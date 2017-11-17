;;made accidentally while translating an example 
;;from Getting Started With Processing" into Quil

(ns cat-tv.cattv-20171116
  (:require [quil.core :as q]
            [quil.middleware :as m]))



(defn setup []
  (q/frame-rate 6)) 

(defn update-state [state]
  state)
  

(defn owl [x y g s]
   (q/push-matrix)
   (q/translate x y)
   (q/scale s) ;; Set the size
   (q/stroke (- 138 g) (- 138 g) (- 125 g)) ;; Set the color value
   (q/stroke-weight 70)
   (q/line 0 -35 0 -65);;Body
   (q/no-stroke)
   (q/fill 255)
   (q/ellipse -17.5 -65 35 35);;Left eye dome
   (q/ellipse 17.5 -65 35 35);;Right eye dome
   (q/arc 0 -65 70 70 0 q/PI);; Chin
   (q/fill 51 51 30)
   (q/ellipse -14 -65 8 8);; Left eye
   (q/ellipse 14 -65 8 8);;  Right eye
   (q/quad 0 -58 4 -51 0 -44 -4 -51);; Beak
   (q/pop-matrix))



(defn draw [state]
   (q/background 176 204 226)
   (doseq [i (range 35 (q/width) (+ 60 (rand 20)))]
          (let [gray (rand-int 102)
                scalar (+ 0.25 (rand 1.75))]
            (owl i 310 gray scalar))))
  
  ;;(if (< (q/frame-count) 72) 
  ;;   (do (println (q/frame-count))
  ;;   (q/save-frame "/target/output-####.png")))
  
 

(q/defsketch practice
    :size [680 400]
    :setup setup
    :update update-state
    :draw draw
    :features [:keep-on-top]
    :middleware [m/fun-mode])

