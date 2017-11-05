(ns cat-tv.cattv-20170305
      (:require  [quil.core :as q]
                 [quil.middleware :as m]))

(def text-vector ["j" "a" "s" "p" "e" "r"]);;text that will be used to compose each blossom. 
                                           ;;please use a factor of 36 texts (1,2,3,4,6,9,12,18,36)   #captainObvious])

(def bloom-spread (/ 36 (count text-vector)))

(defrecord Blossom [x y child-x child-y child-ts child-fill age])

(defn make-blossom [x y age]
  (let [
        max-blossom-spread-x 80      ;; maximum width of blossom in pixels
        max-blossom-spread-y 80      ;; maximum height of blossom in pixels
        max-text-size 40             ;; maximum size for char that is each "petal".
        min-text-lightness 55        ;; minimum lightness for char that is each "petal".
        max-text-lightness 200       ;; maximum lightness for char that is each "petal".
        child-x (vec (take 36 (repeatedly #(+ (- x (/ max-blossom-spread-x 2)) (rand-int max-blossom-spread-x)))))
        child-y (vec (take 36 (repeatedly #(+ (- y (/ max-blossom-spread-y 2)) (rand-int max-blossom-spread-y)))))
        child-ts (vec (take 36 (repeatedly #(+ 1 (rand-int max-text-size)))))
        child-fill (vec (take 36 (repeatedly #(+ min-text-lightness (rand-int (- max-text-lightness min-text-lightness))))))]
  
   (->Blossom x y child-x child-y child-ts child-fill age)))


(defn bloom-blossom [bm txt]
  (doseq [i (range (:age bm))]
    (if (q/mouse-pressed?) 
      (q/fill (- 255 ((:child-fill bm) i)))
      (q/fill ((:child-fill bm) i)))
    (q/text-size ((:child-ts bm) i))
    (q/text txt ((:child-x bm) i) ((:child-y bm) i))))
          

(defn setup []
  (q/frame-rate 24)
  (let [blossom-vector (into [] (for [i (range 36)] (if (= (rem i bloom-spread) 0) (make-blossom (rand-int (q/width)) (rand-int (q/height)) i) "_")))]   
    {:blossom-vector blossom-vector}))
   
(defn update-state [state]
  {:blossom-vector (into [] (for [i (range 36)] 
                                 (if (= (rem i bloom-spread) 0) 
                                     (let [current-blossom (nth (:blossom-vector state) i)]
                                          (if (= (:age current-blossom) 35)
                                              (make-blossom (rand-int (q/width)) (rand-int (q/height)) 0)
                                              (merge current-blossom {:age (inc (:age current-blossom))})))
                                   
                                   "_")))})
  
                                            
(defn draw [state]
  (if (q/mouse-pressed?) 
      (q/background 0)
      (q/background 255))
  
  (doseq [i (range 36)]
    (if (= (rem i bloom-spread) 0) 
      
        (bloom-blossom (nth (:blossom-vector state) i) (nth text-vector (/ i bloom-spread))))))
           
           
    

   

(q/defsketch practice
  :size [400 712]
  :setup setup
  :update update-state
  :draw draw
  :features [:keep-on-top]
  :middleware [m/fun-mode])


