(ns cat-tv.cattv20170225c
      (:require  [quil.core :as q]
                 [quil.middleware :as m]))


(defrecord Bloom [x y child-x child-y child-ts child-fill])

(defn make-bloom [x y]
  (let [spread 80 ;; maximum width of blossom
        child-x (vec (take 36 (repeatedly #(+ (- x (/ spread 2)) (rand-int spread)))))
        child-y (vec (take 36 (repeatedly #(+ (- y 40) (rand-int 81)))))
        child-ts (vec (take 36 (repeatedly #(+ (rand-int 50) 10))))
        child-fill (vec (take 36 (repeatedly #(rand-int 90))))]
    
    (->Bloom x y child-x child-y child-ts child-fill)))
        

(defn blossom-bloom [bm steps txt]
  (doseq [i (range steps)]
         (q/fill ((:child-fill bm) i))
         (q/text-size ((:child-ts bm) i))
         (q/text txt ((:child-x bm) i) ((:child-y bm) i))))
          

(defn setup []
 (q/frame-rate 24) 
 {:b1 (make-bloom 100 100)
  :b2 (make-bloom 100 200)
  :b3 (make-bloom 100 300)})               
   
(defn update-state [state]
   (cond 
     (= (rem (q/frame-count) 36) 0)        (merge state {:b1 (make-bloom (rand-int (q/width)) (rand-int (q/height)))})
     (= (rem (- (q/frame-count) 24) 36) 0) (merge state {:b2 (make-bloom (rand-int (q/width)) (rand-int (q/height)))})
     (= (rem (- (q/frame-count)  12) 36) 0 ) (merge state {:b3 (make-bloom (rand-int (q/width)) (rand-int (q/height)))})
     :else state))
  

     
  
                                            
(defn draw [state]
  (q/background 255)
  (if (> (q/frame-count) 24)
      (do
          (blossom-bloom (:b1 state) (rem (q/frame-count) 36) "x")
          (blossom-bloom (:b2 state) (rem (- (q/frame-count) 24) 36) "y")
          (blossom-bloom (:b3 state) (rem (- (q/frame-count)  12) 36) "z"))))
   

(q/defsketch practice
  :size [400 712]
  :setup setup
  :update update-state
  :draw draw
  :features [:keep-on-top]
  :middleware [m/fun-mode])


