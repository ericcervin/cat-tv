;;based on Parameterize Waves
;;from Form+Code in Design, Art, and Architecture by Reas, etc.

(ns cat-tv.cattv-20170326
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(defn setup [] 
    (q/frame-rate 12)
    (q/stroke 0)
    {:perc-rev-bar 8 ;;percentage of bars that are reversed
     :perc-rev-frame 8 ;;percentage of frames that are reversed})
     :brick-width 20                
     :brick-height 7                
     :cols (/ (q/width) 30)                         
     :rows (/ (q/height) 16)               
     :col-offset 30              
     :row-offset 15                
     :rotation-increment 0.15}) 
                 
(defn draw[state]
  (let [prf (:perc-rev-frame state)
        fore-c (if (>= (rand) (/ prf 100.0)) 0 255)
        back-c (if (>= (rand) (/ prf 100.0)) 255 0)
        brick-width (:brick-width state)
        brick-height (:brick-height state)]                 
    (q/background back-c)
    
    (q/translate 30 30)
    (doseq [i (range (:cols state))]
      (let [r (+ (* -1 q/QUARTER-PI) (rand q/HALF-PI))
            dir 1]
        (q/push-matrix)
        (q/translate (* i (:col-offset state)) 0)
        (doseq [j (range (:rows state))]
          (let [r (+ r (* dir (:rotation-increment state)))]
              (q/push-matrix)
              (q/translate 0 (* j (:row-offset state)))
              (q/rotate r)
              (if (>= (rand) (/ (:perc-rev-bar state) 100.0)) (q/fill back-c) (q/fill fore-c))
              (q/rect (/ brick-width -2) (/ brick-height -2) brick-width brick-height)
              (q/pop-matrix)))
        (q/pop-matrix))))) 


(q/defsketch practice
  :size [400 680]
  :setup setup
  ;;:update update-state
  :draw draw
  :features [:keep-on-top]
  :middleware [m/fun-mode])
