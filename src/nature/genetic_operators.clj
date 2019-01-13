(ns nature.genetic-operators
  "Functions to transform individuals and create"
  (:require [nature.core :as nature]
            [nature.population-presets :as pp])
  (:gen-class))

(defn fitness-based-scanning-allele
  "Pick one of two alleles"
  [allele-1 allele-2 percent]
  (if (<= percent (rand-int 100))
    allele-1
    allele-2))

(defn fitness-based-scanning-genome
  [sequence-1 sequence-2 percent]
  (loop [new-genome []
         gs-1       sequence-1
         gs-2       sequence-2]
    (if (empty? gs-1)
      new-genome
      (recur (conj new-genome (fitness-based-scanning-allele (first gs-1) (first gs-2) percent))
             (rest gs-1)
             (rest gs-2)))))

(defn fitness-based-scanning
  "Construct a new inidiviidual, where each allele is picked from a parent base on the ratio of their fitnesses"
  [fitness-function selected-individuals]
  (let [individual-1 (first selected-individuals)
        individual-2 (second selected-individuals)
        fitness-total (+ (:fitness-score individual-1) (:fitness-score individual-2))
        percent (* 100 (/ (:fitness-score individual-1) fitness-total))]
    (nature/build-individual
     (fitness-based-scanning-genome (:genetic-sequence individual-1)
                                    (:genetic-sequence individual-2)
                                    percent)
     (vector (:guid individual-1) (:guid individual-2))
     pp/default-age
     fitness-function)))

(defn crossover
  "Construct two new individuals by splitting the genetic sequences of two parents and crossing them over wiith each other"
  [fitness-function selected-individuals]
  (let [individual-1 (first selected-individuals)
        individual-2 (second selected-individuals)
        crossover-point (rand-int (count individual-1))
        split-1 (split-at crossover-point (:genetic-sequence individual-1))
        split-2 (split-at crossover-point (:genetic-sequence individual-2))
        parents (vector (:guid individual-1) (:guid individual-2))]
    (list (nature/build-individual (concat (first split-1) (last split-2))
                                   parents
                                   pp/default-age
                                   fitness-function)
          (nature/build-individual (concat (first split-2) (last split-1))
                                   parents
                                   pp/default-age
                                   fitness-function))))

(defn no-op
  "A proxy for `identity` to signal when a particular operation category is not needed,
   but the `selected-individuals` are kept in the pool"
  [fitness-function selected-individuals]
  (map #(inc (:age %)) selected-individuals))

(defn mutation-operator
  "Construct a new individual, by flipping alleles in the genetiic sequence to a random legal allelee"
  [fitness-function allele-set percent individual]
  (nature/build-individual (map #(fitness-based-scanning-allele % (rand-nth allele-set) percent) (:genetic-sequence individual))
                           (:parents individual)
                           (:age individual)
                           fitness-function))
