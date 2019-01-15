(ns nature.fitness-functions
  "Helper functions for common fitness function needs"
  (:require [nature.population-presets :as pp]))

(defn binary-sequence-to-int
  "Convert `binary-seq`, a collection of binary values, to the positive integer it represents"
  [binary-seq]
    (Integer/parseInt (apply str "" binary-seq) 2))
