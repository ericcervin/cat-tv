(ns cat-tv.cattv20170210
      (:require  [quil.core :as q]
                [quil.middleware :as m]))

;;7       | 4    |3    | 1  |11|2 6 8| 0 9 10| 5
;;5 10    | 9    | 0 8 | 6  |2 |11   |1 3 4 7
;;4 7 8 10| 0 9  |5    |3 11|2 |1 6

(defn setup []
  (q/frame-rate 24)
  (let [score   [12 12 12 12 0  12 0  1  2  12 3  12
                 4  12 4  12 4  12 4  12 4  5  12 5
                 12 5  12 5  12 6  12 6  12 6  12 6
                 12 6  12 7  12 7  12 7  12 7  12 8
                 12 8  9  12 9  12 9  12 9  12 9  12
                 12 10 12 10 12 10 11 12 11 12 11 12]]
                 
                 
                 
    {
     
     :score score
     :long_score (vec (concat score (reverse score)))
     
     :image_vec [(q/load-image "/images/green_l0_trans.png")
                 (q/load-image "/images/green_l1_trans.png")
                 (q/load-image "/images/green_l2_trans.png")
                 (q/load-image "/images/green_l3_trans.png")
                 (q/load-image "/images/green_l4_trans.png")
                 (q/load-image "/images/green_l5_trans.png")
                 (q/load-image "/images/green_l6_trans.png")
                 (q/load-image "/images/green_l7_trans.png")
                 (q/load-image "/images/green_l8_trans.png")
                 (q/load-image "/images/green_l9_trans.png")
                 (q/load-image "/images/green_l10_trans.png")
                 (q/load-image "/images/green_l11_trans.png")
                 (q/load-image "/images/green_l_blank.jpg")]
     
     :back_image 0}))
             
  
(defn update-state [state]
  (if (= (rem (q/frame-count) 4) 0) 
    (update state :back_image inc)
    state))

(defn draw [state]
    (let [image_num ((:long_score state) (rem (q/frame-count) 144))
          back_img_num (rem (:back_image state) 12)]
      
         (q/image ((:image_vec state) back_img_num) 0 0 540 960)
         (q/image ((:image_vec state) image_num) 0 0 540 960)))


(q/defsketch practice
  :size [540 960]
  :setup setup
  :update update-state
  :draw draw
  :features [:keep-on-top]
  :middleware [m/fun-mode])


