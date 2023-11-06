(ns nature.initialization-operators
  "Functions to create individuals and populations"
  (:refer-clojure :exclude [uuid])
  (:require [nature.population-presets :as pp]))

(defn uuid
  "Split operator to generate v1 uuids based on runtime env"
  []
  #?(:clj  (str (java.util.UUID/randomUUID))
     :cljs (str (random-uuid))))

(defn generate-sequence
  "Creates a genetic sequence of `sequence-length` elements,
  where each item is in the collection of `alleles`"
  [alleles sequence-length]
  (repeatedly sequence-length #(rand-nth alleles)))

(defn build-individual
  "Generate a new individual, and evaluate the fitness of the genetic sequence."
  ([genetic-sequence fitness-function]
   (assoc {} :genetic-sequence genetic-sequence
          :guid (uuid)
          :parents pp/initializer-name
          :age pp/default-age
          :fitness-score (fitness-function genetic-sequence)))

  ([genetic-sequence parent-coll age fitness-function]
   (assoc {} :genetic-sequence genetic-sequence
          :guid (uuid)
          :parents parent-coll
          :age age
          :fitness-score (fitness-function genetic-sequence))))

(defn build-population
  "Build `population-size` individuals by invoking `build-individual` on random, conforming genetic sequences.
  If given a sequence generator function, uses this function to generate valid sequences instead."
  ([population-size sequence-generator-function fitness-function]
   (repeatedly population-size #(build-individual (sequence-generator-function) fitness-function)))
  
  ([population-size alleles sequence-length fitness-function]
   (repeatedly population-size #(build-individual (generate-sequence alleles sequence-length) fitness-function))))
