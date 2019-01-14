(ns nature.example
  (:require [nature.core :as nature]
            [nature.population-presets :as pp])
  (:gen-class))

(defn example-evolution
  []
  (nature/evolve pp/binary-genome
                 pp/default-sequence-length
                 pp/default-population-length
                 pp/default-generation-count
                 pp/sum-alleles
                 [(partial go/crossover pp/sum-alleles)]
                 [(partial go/mutation-operator pp/sum-alleles pp/binary-genome 1)]))
