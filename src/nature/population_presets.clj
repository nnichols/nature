(ns nature.population-presets)

(def initializer-name
  (vector "Initializer"))

(def default-age
  0)

(def binary-genome
  [0 1])

(defn integer-genome
  ([] (range 10))
  ([top] (range top))
  ([top bottom] (range top bottom))
  ([top bottom step] (range top bottom step)))
