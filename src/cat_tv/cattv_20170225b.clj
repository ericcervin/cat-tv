(ns cat-tv.cattv20170225b
      (:require  [quil.core :as q]
                 [quil.middleware :as m]))


(defrecord Bloom [x y child-x child-y child-ts child-fill])

(defn make-bloom [x y]
  (let [ 
        child-x (vec (take 24 (repeatedly #(+ (- x 27) (rand-int 54)))))
        child-y (vec (take 24 (repeatedly #(+ (- y 27) (rand-int 54)))))
        child-ts (vec (take 24 (repeatedly #(+ (rand-int 30) 10))))
        child-fill (vec (take 24 (repeatedly #(rand-int 90))))]
    
    (->Bloom x y child-x child-y child-ts child-fill)))
        

(defn blossom-bloom [bm steps]
  (doseq [i (range steps)]
         (q/fill ((:child-fill bm) i))
         (q/text-size ((:child-ts bm) i))
         (q/text "*" ((:child-x bm) i) ((:child-y bm) i))))
          

(defn setup []
 (q/frame-rate 24) 
 {:b1 (make-bloom 100 100)
  :b2 (make-bloom 100 200)
  :b3 (make-bloom 100 300)})               
   
(defn update-state [state]
   (cond 
     (= (rem (q/frame-count) 24) 0)        (merge state {:b1 (make-bloom (rand-int 200) (rand-int 356))})
     (= (rem (- (q/frame-count) 12) 24) 0) (merge state {:b2 (make-bloom (rand-int 200) (rand-int 356))})
     (= (rem (- (q/frame-count)  8) 24) 0 ) (merge state {:b3 (make-bloom (rand-int 200) (rand-int 356))})
     :else state))
  

     
  
                                            
(defn draw [state]
  (q/background 255)
  (if (> (q/frame-count) 12)
      (do
          (blossom-bloom (:b1 state) (rem (q/frame-count) 24))
          (blossom-bloom (:b2 state) (rem (- (q/frame-count) 12) 24))
          (blossom-bloom (:b3 state) (rem (- (q/frame-count)  8) 24)))))
   

(q/defsketch practice
  :size [200 356]
  :setup setup
  :update update-state
  :draw draw
  :features [:keep-on-top]
  :middleware [m/fun-mode])


