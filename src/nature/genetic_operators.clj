(ns nature.genetic-operators
  (:require [nature.core :as nature]
            [nature.population-presets :as pp])
  (:gen-class))

(defn fitness-based-scanning-allele
  "Pick one of two alleles"
  [allele-1 allele-2 percent-1 percent-2]
  (if (< percent-1 (rand-int 100))
    allele-1
    allele-2))

(defn fitness-based-scanning-genome
  [individual-1 individual-2 percent-1 percent-2]
  (loop [new-genome '()
         ind-1      individual-1
         ind-2      individual-2]
    (if (empty? ind-1)
      new-genome
      (recur (cons (fitness-based-scanning-allele (first ind-1) (first ind-2) percent-1 percent-2) new-genome)
             (rest ind-1)
             (rest ind-2)))))

(defn crossover
  [individual-1 individual-2 fitness-function]
  (let [crossover-point (rand-int (count individual-1))
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

(defn fitness-based-scanning
  [individual-1 individual-2 fitness-function]
  (let [fitness-total (+ (:fitness-score individual-1) (:fitness-score individual-2))
        percent-1 (* 100 (/ (:fitness-score individual-1) fitness-total))
        percent-2 (- 100 percent-1)]
    (nature/build-individual
     (fitness-based-scanning-genome (:genetic-sequence individual-1)
                                    (:genetic-sequence individual-2)
                                    percent-1
                                    percent-2)
     (vector (:guid individual-1) (:guid individual-2))
     pp/default-age
     fitness-function)))
