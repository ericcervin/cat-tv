(ns cat-tv.cattv-20170413
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(defn set-xyz []
    {:place-x  (rand-int 600)
     :place-y  (rand-int 600)
     :place-z  (rand-int 600)})


(defn setup [] 
  (set-xyz))

(defn update-state [state]
  (if (= (rem (q/frame-count) 15) 0)
      (set-xyz)
      state))
  
  

(defn draw [state]
  (q/background 25)
  (let [value (rem (q/frame-count) 300)]
       (q/translate (+ (* (q/sin value) 10) (:place-x state)) 
                    (:place-y state) 
                    (- (* (q/cos value) 10) (:place-z state)))
         
    (q/fill 255)
    (q/sphere 50)))
    
    
  

(q/defsketch practice
  :size [600 600]
  :setup setup
  :update update-state
  :renderer :p3d
  :draw draw
  :features [:keep-on-top]
  :middleware [m/fun-mode])


