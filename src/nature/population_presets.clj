(ns nature.population-presets
  "Commonly used alleles and settings to help reign in magic constants")

(def initializer-name
  (vector "Initializer"))

(def default-age
  0)

(def binary-genome
  [0 1])

(defn integer-genome
  "A loose proxy for `range` with defaults for common needs/generative testing"
  ([] (map inc (range 10)))
  ([top] (map inc (range top)))
  ([top bottom] (range bottom (inc top)))
  ([top bottom step] (range bottom (inc top) step)))

(def sum-alleles
  (partial apply +))

(def default-sequence-length
  25)

(def default-population-size
  50)

(def default-generation-count
  50)
