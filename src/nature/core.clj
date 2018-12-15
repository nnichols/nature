(ns nature.core
  (:require [nature.spec :as s]
            [nature.population-presets :as pp]
            [bigml.sampling.simple :as bss])
  (:gen-class))

(defn uuid
  "More idiomatic wrapper around Java's v1 UUID functionality"
  []
  (str (java.util.UUID/randomUUID)))

(defn generate-sequence
  "Creates a genetic sequence of `sequence-length` elements,
  where each item is in the collection of `alleles`"
  [alleles sequence-length]
  (repeatedly sequence-length #(rand-nth alleles)))

(defn build-individual
  "Create a generated individual"
  ([genetic-sequence fitness-function]
   (assoc {}
          :genetic-sequence genetic-sequence
          :guid (uuid)
          :parents pp/initializer-name
          :age pp/default-age
          :fitness-score (fitness-function genetic-sequence)))

  ([genetic-sequence parents age fitness-function]
   (assoc {}
          :genetic-sequence genetic-sequence
          :guid (uuid)
          :parents parents
          :age age
          :fitness-score (fitness-function genetic-sequence))))

(defn build-population
  [population-size alleles sequence-length fitness-function]
  (repeatedly population-size #(build-individual (generate-sequence alleles sequence-length) fitness-function)))

(defn weighted-selection-of-population
  ([population total-retrieved]
   (take total-retrieved (bss/sample population :weigh #(:fitness-score %) :replace true)))
  ([population total-retrieved replace?]
   (take total-retrieved (bss/sample population :weigh #(:fitness-score %) :replace replace?))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [population (build-population 20 [0 1] 10 (partial apply +))]
    (println (loop [new-population '()]
               (if (>= (count new-population) (count population))
                 new-population
                 (recur (concat new-population (weighted-selection-of-population population 4))))))))
