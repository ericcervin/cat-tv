;;based on Repeat: Recursive Tree
;;from Form+Code in Design, Art, and Architecture by Reas, etc.

(ns getting-started-with-quil.cattv-20170324
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(declare seed1)
(declare seed2)

(defn seed1 [dot-size angle x y angle-offset-a angle-offset-b]
  (if (> dot-size 1.0)
    (if (> (rand 1.0) 0.2)
      (let [new-x  (+ x (* (q/cos angle) dot-size))
            new-y  (+ y (* (q/sin angle) dot-size))]
        (q/ellipse x y dot-size dot-size)
        (seed1 (* dot-size 0.99) (- angle angle-offset-a) new-x new-y angle-offset-a angle-offset-b))
      
      (let [new-x  (+ x (q/cos angle))
            new-y  (+ y (q/sin angle))]
        (q/ellipse x y dot-size dot-size)
        (seed2 (* dot-size 0.99) (+ angle angle-offset-a) new-x new-y angle-offset-a angle-offset-b)
        (seed1 (* dot-size 0.60) (+ angle angle-offset-b) new-x new-y angle-offset-a angle-offset-b)
        (seed2 (* dot-size 0.50) (- angle angle-offset-b) new-x new-y angle-offset-a angle-offset-b)))))              

(defn seed2 [dot-size angle x y angle-offset-a angle-offset-b]
   (if (> dot-size 1.0)
       (if (> (rand 1.0) 0.5)
           (let [new-x  (+ x (* (q/cos angle) dot-size))
                 new-y  (+ y (* (q/sin angle) dot-size))]
                (q/ellipse x y dot-size dot-size)
                (seed2 (* dot-size 0.99) (+ angle angle-offset-a) new-x new-y angle-offset-a angle-offset-b))
                         
           (let [new-x  (+ x (q/cos angle))
                 new-y  (+ y (q/sin angle))]
                (q/ellipse x y dot-size dot-size)
                (seed1 (* dot-size 0.99) (+ angle angle-offset-a) new-x new-y angle-offset-a angle-offset-b)
                (seed2 (* dot-size 0.60) (+ angle angle-offset-b) new-x new-y angle-offset-a angle-offset-b)
                (seed1 (* dot-size 0.50) (- angle angle-offset-b) new-x new-y angle-offset-a angle-offset-b)))))

(defn setup []
  (q/frame-rate 8)
  (q/no-stroke)
  {:dot-size 8
   :angle-offset-a (q/radians 1)
   :angle-offset-b (q/radians 30)})  
 
(defn update-state [state]
  state)        
                                
(defn draw [state]
  (if (< (rand 1) 0.08) 
    (do (q/fill 0) (q/background 255))
    (do (q/fill 255 ) (q/background 0)))
  
  (q/translate (/ (q/width) 1.1) (q/height))
  (seed1 (:dot-size state) (q/radians 270) 0 0 (:angle-offset-a state) (:angle-offset-b state)))

(q/defsketch practice
  :size [400 700]
  :setup setup
  :update update-state
  :draw draw
  :features [:keep-on-top]
  :middleware [m/fun-mode])


