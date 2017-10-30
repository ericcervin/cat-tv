(ns cat-tv.cattv20170210
      (:require  [quil.core :as q]
                [quil.middleware :as m]))


(defn setup []
  (let [back-image (q/load-image "/images/green_l_blank.jpg")]
      (q/frame-rate 24)
      (q/image back-image 0 0 200 356)
      {:front-image (q/load-image "/images/lil_red.png")
       :back-image back-image 
       :x (rand-int 100)
       :y (rand-int 100)}))               
   
             
  
(defn update-state [state]
    (if (= (rem (q/frame-count) 24) 0) 
        (merge state {:x (rand-int 100) :y (rand-int 100)})
        state))      
      
      

(defn draw [state]
  (if (= (rem (q/frame-count) 24) 0) (q/image (:back-image state) 0 0 200 356)) 
  (q/image (:front-image state) (+ (:x state) (rand-int 54)) (+ (:y state) (rand-int 54)) 20 20))
  
    


(q/defsketch practice
  :size [200 356]
  :setup setup
  :update update-state
  :draw draw
  :features [:keep-on-top]
  :middleware [m/fun-mode])


