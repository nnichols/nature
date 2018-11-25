(ns nature.core
  (:require [nature.spec :as s]
            [bigml.sampling.simple :as bss])
  (:gen-class))

(defn uuid
  []
  (str (java.util.UUID/randomUUID)))

(defn generate-sequence
  [alleles sequence-length]
  (repeatedly sequence-length #(rand-nth alleles)))

(defn build-individual
  "Create a generated individual"
  [alleles sequence-length fitness-function]
  (let [genes (generate-sequence alleles sequence-length)]
    (assoc {}
           :genetic-sequence genes
           :guid (uuid)
           :parents ["Initializer"]
           :age 0
           :fitness-score (fitness-function genes))))

(defn build-population
  [population-size alleles sequence-length fitness-function]
  (repeatedly population-size
              #(build-individual alleles sequence-length fitness-function)))

(defn weighted-selection-of-population
  ([population total-retrieved]
   (take total-retrieved (bss/sample population :weigh #(:fitness-score %) :replace true)))
  ([population total-retrieved replace?]
   (take total-retrieved (bss/sample population :weigh #(:fitness-score %) :replace replace?))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (run! println
        (weighted-selection-of-population
         (build-population 500 [1 0] 500 (partial apply +))
         10
         true)))
