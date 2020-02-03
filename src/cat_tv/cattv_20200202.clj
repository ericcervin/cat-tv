;;based on ex. 24-12 in Processing: A Programming Handbook 

(ns cat-tv.cattv-20200202
  (:require [quil.core :as q]))
           ;; [quil.middleware :as m]))

(defn setup []
  (q/frame-rate 8))
    
(defn draw-x [gray weight x y size]
    (q/stroke gray)
    (q/stroke-weight weight)
    (q/line x y (+ x size) (+ y size))
    (q/line (+ x size) y x (+ y size)))


(defn draw []
  (q/background 204)
  (doseq [i (range 36)]
    (let [gray-value (rand-int 255)
          thickness  (rand-int 30)
          x (- (rand-int (+ 50 (q/width))) 50)
          y (- (rand-int (+ 50 (q/height))) 50)]
      (draw-x gray-value thickness x y 100))))
          

(q/defsketch practice
  :size [680 400]
  :setup setup
  ;;:update update
  :draw draw
  :features [:keep-on-top])
  ;;:middleware [m/fun-mode])
  
