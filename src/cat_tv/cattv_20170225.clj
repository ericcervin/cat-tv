(ns cat-tv.cattv20170225
      (:require  [quil.core :as q]
                 [quil.middleware :as m]))


(defn setup []
  (let [back-image (q/load-image "/images/green_l_blank.jpg")]
      (q/frame-rate 24)
      (q/image back-image 0 0 200 178)
      (q/image back-image 0 178 200 178)
      {:front-image (q/load-image "/images/lil_red.png")
       :back-image back-image 
       :x (rand-int 100)
       :y (rand-int 90)
       :x2 (rand-int 100)
       :y2 (+ (rand-int 90) 178)}))               
   
(defn update-state [state]
  (cond (= (rem (q/frame-count) 12) 0)
        (do (q/image (:back-image state) 0 0 200 178)(merge state {:x (rand-int 100)  :y  (rand-int 90)}))
        (= (rem (- (q/frame-count) 6) 12) 0) 
        (do (q/image (:back-image state) 0 178 200 178)(merge state {:x2 (rand-int 100) :y2 (+ (rand-int 90) 178)}))
        :else state))
  
                                            
(defn draw [state]
  (let  [hw (+ (rand-int 35))
         x (:x state)
         y (:y state)
         x2 (:x2 state)
         y2 (:y2 state)]
        
        (q/image (:front-image state) (+ x (rand-int 54)) (+ y (rand-int 54)) hw hw)
        (q/image (:front-image state) (+ x2 (rand-int 54)) (+ y2 (rand-int 54)) hw hw))) 
  
  
    


(q/defsketch practice
  :size [200 356]
  :setup setup
  :update update-state
  :draw draw
  :features [:keep-on-top]
  :middleware [m/fun-mode])


