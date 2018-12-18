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
  "Generate a new individual, and evaluate the fitness of the genetic sequence."
  ([genetic-sequence fitness-function]
   (assoc {}
          :genetic-sequence genetic-sequence
          :guid (uuid)
          :parents pp/initializer-name
          :age pp/default-age
          :fitness-score (fitness-function genetic-sequence)))

  ([genetic-sequence parent-coll age fitness-function]
   (assoc {}
          :genetic-sequence genetic-sequence
          :guid (uuid)
          :parents parent-coll
          :age age
          :fitness-score (fitness-function genetic-sequence))))

(defn build-population
  "Build `population-size` individuals by invoking `build-individual` on random, conforming genetic sequences."
  [population-size alleles sequence-length fitness-function]
  (repeatedly population-size #(build-individual (generate-sequence alleles sequence-length) fitness-function)))

(defn weighted-selection-of-population
  "Pick `total-retreived` individuals from `population` with a relative probability of
  the individual's fitness score divided by the population's aggregate fitness score"
  ([population total-retrieved]
   (take total-retrieved (bss/sample population :weigh #(:fitness-score %) :replace true)))
  ([population total-retrieved replace?]
   (take total-retrieved (bss/sample population :weigh #(:fitness-score %) :replace replace?))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "¯\\_(ツ)_/¯"))
