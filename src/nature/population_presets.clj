(ns nature.population-presets)

(def binary-genome
  [0 1])

(defn integer-genome
  ([] (range 10))
  ([max] (range max))
  ([max min] (range max min))
  ([max min step] (range max min step)))
