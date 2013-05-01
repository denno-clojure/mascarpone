(ns music.inverter)

(defn step
    "a stepper, which pops off the first item and adds it to
    the end. there's probably something in clojure.core that
    does this, should investigate more."
    ([coll]
    (conj (vec (rest coll)) (first coll)))
    ([coll steps]
            (cond 
                (zero? steps) coll
                (< steps 0) (recur (step coll (dec (count coll))) (inc steps)) 
                :else (recur (step coll) (dec steps)))))

(defmulti transposer (fn [_ thing] (class thing)))

(defmethod transposer
    Long [scale degree]
    (cond
        (zero? degree) scale
        (< degree 0) (recur (step scale (dec (count scale))) (inc degree))
        :else (recur (step scale) (dec degree))))

(defmethod transposer 
    clojure.lang.Keyword [scale note]
    (loop [new-scale scale]
    (cond 
        (= (first new-scale) note) new-scale 
        :else (recur (step new-scale)))))

(defn scale-stepper
    "step through a scale given an intervallic recipe.
    the recipe should be an intervallic sequence:
    common tone, second, second would be expressed as [0 1 1].
    "
    ([scale recipe]
    (let [recipe-step (if (neg? (first recipe)) -1 1)] 
    (lazy-seq 
        (cons 
            (first scale) 
            (scale-stepper (take 
                (count scale) 
                (cycle (transposer scale (first recipe)))) (transposer recipe recipe-step))
    )))))

(defn make-scale
    "make a scale using a voicing, scale and degree.
    this is really an implementation detail and probably
    not directly useful."
    [recipe voicing scale degree]
    (take (count scale) 
    (scale-stepper (transposer scale (nth voicing degree)) (step recipe degree))))

(defn inverter
    "create a chord scale given an intervallic recipe, spelling of chord,
    and the scale to use"
    [recipe spelling scale]
    (let
    [voicing (map dec spelling)
    counts (range (count voicing))]
    (apply map vector
        (map (partial make-scale recipe voicing scale) counts))))

