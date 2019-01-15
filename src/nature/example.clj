(ns nature.example
  (:require [nature.core :as nature]
            [nature.genetic-operators :as go]
            [nature.population-presets :as pp]))

(defn example-evolution
  []
  (nature/evolve pp/binary-genome
                 pp/default-sequence-length
                 pp/default-population-size
                 pp/default-generation-count
                 pp/sum-alleles
                 [(go/crossover pp/sum-alleles)]
                 [(partial go/mutation-operator pp/sum-alleles pp/binary-genome 1)]))
