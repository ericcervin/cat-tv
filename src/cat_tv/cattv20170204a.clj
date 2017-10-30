(ns cat-tv.cattv20170204a
      (:require  [quil.core :as q]
                [quil.middleware :as m]))

(defn setup []
  (q/frame-rate 24)
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
               (q/load-image "/images/green_l11.jpg")
               (q/load-image "/images/green_l_blank.jpg")]
   
   :score [12 12 12 12 12 12 12 0  12 0  12 0
           12 0  12 0  1  12 1  12 1  12 1  12
           1  12 1  2  12 2  12 2  12 2  12 2
           12 3  12 3  12 3  12 3  12 3  12 3
           12 3  12 3  12 3  12 3  12 3  12 4
           12 4  5  12 5  12 6  12 7  12 7  12
           8  12 8  12 8  12 8  12 8  9  10 12
           10 12 10 12 11 12 11 12 11 12 11 12]})
               
  
(defn update-state [state]
  state)

(defn draw [state]
    (let [image_num ((:score state) (rem (q/frame-count) 96))] 
         (q/image ((:image_vec state) image_num) 0 0 540 960)))


(q/defsketch practice
  :size [540 960]
  :setup setup
  :update update-state
  :draw draw
  :features [:keep-on-top]
  :middleware [m/fun-mode])


