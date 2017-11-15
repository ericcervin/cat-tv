;;based on Simulate Particles
;;from Form+Code in Design, Art, and Architecture by Reas, etc.


(ns getting-started-with-quil.cattv-20170409
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(defrecord PosVector [x y]);;try to cook up a substitute for the PVector in Processing
(defrecord Particle [loc vel acc hist randmin randmax counter r x y q])

(defn add-posvectors [v1 v2]
   (->PosVector (+ (:x v1) (:x v2)) (+ (:y v1) (:y v2))))
  
(defn make-particle [locpos]
   (let [randmin (* -1 q/HALF-PI)
         randmax 0
         counter 0
         r (rand q/TWO-PI)
         x (q/cos r)
         y (q/sin r)
         acc (->PosVector (/ x 15) (/ y 15))
         q (rand)
         r (+ randmin (rand (- randmax randmin)))
         x (* (q/cos r) q)
         y (* (q/sin r) q)
         vel (->PosVector x y)
         loc locpos
         hist []]
     
     (->Particle loc vel acc hist randmin randmax counter r x y q)))
  

(defn update-particle [p particle_count]
    (let [vel (add-posvectors (:vel p) (:acc p))
          loc (add-posvectors (:loc p) (:vel p))
          ;;save location every 10 frames
          hist (if (and (= (rem (q/frame-count) 10) 0) (< (:counter p) particle_count))
                   (conj (:hist p) (:loc p))
                   (:hist p))
          counter (if (and (= (rem (q/frame-count) 10) 0) (< (:counter p) particle_count))
                    (inc (:counter p))
                    (:counter p))]
      (merge p {:vel vel :loc loc :hist hist :counter counter})))

(defn draw-particle [p]
   (q/fill 100 50)
   (q/no-fill)
    ;;draw history path
   (q/stroke 0 100)
   (q/begin-shape)
   (doseq [v (range (:counter p))]
         (q/vertex (:x (nth (:hist p) v)) (:y (nth (:hist p) v))))
   (if (> (:counter p) 0 ) (q/vertex (:x (:loc p)) (:y (:loc p))))
   (q/end-shape))



(defn birth []
  (let [particle-count 250 ;;number of lines in the drawing
        life-span 75 ;;number of frames before rebirth (~60 frames/second)]
        start-x (rand (q/width))
        start-y (rand (q/height))
        particles (vec (take particle-count (repeatedly #(make-particle (->PosVector start-x start-y)))))]
    {:particle-count particle-count 
     :life-span life-span
     :particles particles}))

(defn setup []
  (birth))

(defn update-state [state]
  (if (= (rem (q/frame-count) (:life-span state)) 0)
      (birth)
      (merge state {:particles (vec (map #(update-particle % (:particle-count state)) (:particles state)))})))
    

(defn draw [state]
  (let [particles (:particles state)]
    
    (q/background 255)
    (doseq [v (range (count particles))]
      (draw-particle (nth particles v)))))
                                 

(q/defsketch practice
  :size [180 320]
  :setup setup
  :update update-state
  :draw draw
  :features [:keep-on-top]
  :middleware [m/fun-mode])
