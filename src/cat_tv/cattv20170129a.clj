(ns cat-tv.cattv20170129a 
   (require [quil.core :as q]
            [quil.middleware :as m]))

(defn setup []
  (q/frame-rate 12)
  {:image_vec [(q/load-image "/images/green_l0.jpg")
               (q/load-image "/images/green_l1.jpg")
               (q/load-image "/images/green_l2.jpg")
               (q/load-image "/images/green_l3.jpg")
               (q/load-image "/images/green_l4.jpg")
               (q/load-image "/images/green_l5.jpg")
               (q/load-image "/images/green_l6.jpg")
               (q/load-image "/images/green_l7.jpg")
               (q/load-image "/images/green_l8.jpg")
               (q/load-image "/images/green_l9.jpg")
               (q/load-image "/images/green_l10.jpg")
               (q/load-image "/images/green_l11.jpg")]})
               
  
(defn update-state [state]
  state)

(defn draw [state]
    (q/image ((:image_vec state) (rem (q/frame-count) 12)) 0 0 540 960))


(q/defsketch practice
  :size [540 960]
  :setup setup
  :update update-state
  :draw draw
  :features [:keep-on-top]
  :middleware [m/fun-mode])


