(ns cat-tv.cattv-20170303
      (:require  [quil.core :as q]
                 [quil.middleware :as m]))


(defrecord Blossom [x y child-x child-y child-ts child-fill])

(defn make-blossom [x y]
  (let [max-blossom-spread-x 80      ;; maximum width of blossom in pixels
        max-blossom-spread-y 80      ;; maximum height of blossom in pixels
        max-text-size 40             ;; maximum size for char that is each "petal".
        min-text-lightness 55        ;; minimum lightness for char that is each "petal".
        max-text-lightness 200       ;; maximum lightness for char that is each "petal".
        child-x (vec (take 36 (repeatedly #(+ (- x (/ max-blossom-spread-x 2)) (rand-int max-blossom-spread-x)))))
        child-y (vec (take 36 (repeatedly #(+ (- y (/ max-blossom-spread-y 2)) (rand-int max-blossom-spread-y)))))
        child-ts (vec (take 36 (repeatedly #(+ 1 (rand-int max-text-size)))))
        child-fill (vec (take 36 (repeatedly #(+ min-text-lightness (rand-int (- max-text-lightness min-text-lightness))))))]
    
    (->Blossom x y child-x child-y child-ts child-fill)))
        

(defn bloom-blossom [bm steps txt]
  (doseq [i (range steps)]
         (if (q/mouse-pressed?) 
             (q/fill (- 255 ((:child-fill bm) i)))
             (q/fill ((:child-fill bm) i)))
         (q/text-size ((:child-ts bm) i))
         (q/text txt ((:child-x bm) i) ((:child-y bm) i))))
          

(defn setup []
  (q/frame-rate 24) 
  {:b1 (make-blossom 100 100)
   :b2 (make-blossom 125 125)
   :b3 (make-blossom 150 150)})               
   
(defn update-state [state]
   (cond 
     (= (rem (q/frame-count) 36) 0)        (merge state {:b1 (make-blossom (rand-int (q/width)) (rand-int (q/height)))})
     (= (rem (- (q/frame-count) 24) 36) 0) (merge state {:b2 (make-blossom (rand-int (q/width)) (rand-int (q/height)))})
     (= (rem (- (q/frame-count)  12) 36) 0 ) (merge state {:b3 (make-blossom (rand-int (q/width)) (rand-int (q/height)))})
     :else state))
  

     
  
                                            
(defn draw [state]
  (if (q/mouse-pressed?) 
      (q/background 0)
      (q/background 255))
                      
  
  (if (> (q/frame-count) 24)
    (do
      (bloom-blossom (:b1 state) (rem (q/frame-count) 36) "x")
      (bloom-blossom (:b2 state) (rem (- (q/frame-count) 24) 36) "y")
      (bloom-blossom (:b3 state) (rem (- (q/frame-count)  12) 36) "z"))))
   

(q/defsketch practice
  :size [400 712]
  :setup setup
  :update update-state
  :draw draw
  :features [:keep-on-top]
  :middleware [m/fun-mode])


