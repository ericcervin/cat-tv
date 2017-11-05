(ns cat-tv.cattv-20170310
  (:require  [quil.core :as q]
            [quil.middleware :as m]))                              


(defrecord Letter-Splash [x y age chr child-diameter child-hue])

(defn make-letter-splash [x y age chr]
  (let [
        min-circ-lightness 55         ;;minimum lightness for each circle
        max-circ-lightness 200        ;;maximum lightness for each circle
        max-circ-diameter  200        ;;maximum diameter of circles around each letter
        child-diameter  (vec (take 36 (repeatedly #(+ 26 (- (rand-int max-circ-diameter) 26)))))
        child-hue       (vec (take 36 (repeatedly #(+ min-circ-lightness (- (rand-int max-circ-lightness) min-circ-lightness)))))]
        
  
   (->Letter-Splash x y age chr child-diameter child-hue)))


(defn splash-letter-splash [ls]
  (q/text-size 20)
  
  (if (q/mouse-pressed?) 
      (q/fill 255)
      (q/fill 0))
  (q/text (str (char (:chr ls))) (- (:x ls) 5) (+ (:y ls) 5))
  
  (q/no-fill)
  (q/stroke-weight 2)
  (q/ellipse-mode :center)
  (doseq [i (range (+ (:age ls) 1))]
    (let [child-hue (nth (:child-hue ls) i)
          child-diameter (nth (:child-diameter ls) i)]
      (if (q/mouse-pressed?)
          (q/stroke (- 255 child-hue))
          (q/stroke child-hue))
      (q/ellipse (:x ls) (:y ls) child-diameter child-diameter))))
         

(defn setup []
  (q/frame-rate 24)
  (let [
        max-population 3 ;;max number of letters that will appear on the screen at once                
                         ;;please use a factor of 36 (1,2,3,4,6,9,12,18,36)   #captainObvious) 
        letter-spread  (/ 36 max-population)
        letter-splash-vector (into [] (for [i (range 36)] (if (= (rem i letter-spread) 0) 
                                                              (make-letter-splash (rand-int (q/width)) (rand-int (q/height)) i (+ 96 (/ i letter-spread))) 
                                                              "_")))]   
       
       {:letter-splash-vector letter-splash-vector
        :letter-spread letter-spread}))  

(defn update-state [state]
  
  (let [
        letter-spread (:letter-spread state)
        letter-splash-vector (:letter-splash-vector state)]
        
    
    {:letter-splash-vector (into [] (for [i (range 36)] 
                                     (if (= (rem i letter-spread) 0) 
                                       (let [current-splash (nth letter-splash-vector  i)
                                             current-ascii (:chr current-splash)]   
                                         (if (= (:age current-splash) 35)
                                           (make-letter-splash (rand-int (q/width)) 
                                                               (rand-int (q/height)) 
                                                               0 
                                                               (if (= current-ascii 122) 96 (inc current-ascii)))
                                           (merge current-splash {:age (inc (:age current-splash))})))
                                       
                                       "_")))
     
     :letter-spread letter-spread}))
  
                                            
(defn draw [state]
  (let [
        letter-spread (:letter-spread state)
        letter-splash-vector (:letter-splash-vector state)]
      
       (if (q/mouse-pressed?) 
           (q/background 0)
           (q/background 255))
  
       (doseq [i (range 36)]
              (if (= (rem i letter-spread) 0) 
                
                  (splash-letter-splash (nth letter-splash-vector i))))))
                
         
           

(q/defsketch practice
  :size [200 356]
  :setup setup
  :update update-state
  :draw draw
  :features [:keep-on-top]
  :middleware [m/fun-mode])


