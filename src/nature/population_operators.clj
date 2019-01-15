(ns nature.population-operators
  "Functions that span or operate against entire populations"
  (:require [nature.population-presets :as pp]
            [bigml.sampling.simple :as bss]))

(defn keep-elite
  "Find the top `number-to-keep` individuals in `population`, and increment their ages by 1"
  [population number-to-keep]
  (map #(update % :age inc) (take number-to-keep (sort-by :fitness-score #(> %1 %2) population))))

(defn weighted-selection-of-population
  "Pick `total-retreived` individuals from `population` with a relative probability of
  the individual's fitness score divided by the population's aggregate fitness score"
  ([population total-retrieved]
   (take total-retrieved (bss/sample population :weigh #(:fitness-score %) :replace true)))
  ([population total-retrieved replace?]
   (take total-retrieved (bss/sample population :weigh #(:fitness-score %) :replace replace?))))

(defn advance-generation
  "Apply the functions in `binary-operator-set` until a sufficiently large population is built.
   Then apply functions in `unary-operator-set` to the result.
   Optionally, include a map of `settings` to guide overall behavior.
   If the `:carry-over` setting is added, the elite member `n` of the prior generation will be advanced to the next generation."
  ([population binary-operator-set unary-operator-set]
   (let [binary-pop (repeatedly (count population) #(apply (rand-nth binary-operator-set) [(weighted-selection-of-population population 2)]))]
     (map #(apply (rand-nth unary-operator-set) [%]) binary-pop)))
  ([population binary-operator-set unary-operator-set settings]
  ;; Currently, :carry-over is the only optional behavior
   (if (> (:carry-over settings) 0)
     (concat (advance-generation population binary-operator-set unary-operator-set) (keep-elite population (:carry-over settings)))
     (advance-generation population binary-operator-set unary-operator-set))))
