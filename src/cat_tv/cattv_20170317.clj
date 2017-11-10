(ns cat-tv.cattv-20170317
  (:require  [quil.core :as q]
             [quil.middleware :as m]))              



(defrecord Planetoid [x y o-rate p-diameter o-diameter])

(defn render-planetoid [ptd]
    ;;line of moon's orbit
    (q/no-fill)
    (q/stroke (+ 100 (rand-int 155)))
    (q/ellipse (:x ptd) (:y ptd) (:o-diameter ptd) (:o-diameter ptd))
    
    ;;planet
    (q/fill (+ 100 (rand-int 155)) (+ 100 (rand-int 155)))
    (q/ellipse (:x ptd) (:y ptd) (:p-diameter ptd) (:p-diameter ptd))
    
    ;;moon
    (let [rads (q/radians (* (q/frame-count) (:o-rate ptd))) 
          ht (+ (:y ptd) (* (q/sin rads) (/ (:o-diameter ptd) 2)))
          wt (+ (:x ptd) (* (q/cos rads) (/ (:o-diameter ptd) 2)))]
          
         (q/fill (+ 100 (rand-int 155)) (+ 100 (rand-int 155)))
         (q/ellipse wt ht 10 10)))

(defn make-planetoid []
  (let [x (rand-int (q/width))
        y (rand-int (q/height))
        orbit-rate (+ 4 (rand-int 20))
        planet-diameter (+ 15 (rand-int 15))
        orbit-diameter  (+ planet-diameter (* planet-diameter (+ 1 (rand-int 3))))]
    
    (->Planetoid x y orbit-rate planet-diameter orbit-diameter)))
    
(defn make-planet-system []
  (let [planet-count 9 ;; number of planets in system
        planet-system (vec (take planet-count (repeatedly make-planetoid)))]
    
    planet-system))      
  
(defn setup []
  (q/frame-rate 24)
  (make-planet-system))

(defn draw [state]
  (q/background 0)
  (doseq [p state]
    (render-planetoid p)))
  
(defn update-state [state]
  (if (= (rem (q/frame-count) 24) 0)
      (make-planet-system)
      state))

(q/defsketch practice
  :size [200 356]
  :setup setup
  :update update-state
  :draw draw
  :features [:keep-on-top]
  :middleware [m/fun-mode])
