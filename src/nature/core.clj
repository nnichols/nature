(ns nature.core
  (:require [nature.spec :as s])
  (:gen-class))

(defn uuid
  []
  (str (java.util.UUID/randomUUID)))

(defn generate-sequence
  [allele-set sequence-length]
  (repeatedly sequence-length #(rand-nth allele-set)))

(defn build-individual
  "Create a generated individual"
  [allele-set sequence-length fitness-function]
  (let [genes (generate-sequence allele-set sequence-length)]
    (assoc {} :genetic-sequence genes
           :guid (uuid)
           :parents ["Initializer"]
           :age 0
           :fitness-score (fitness-function genes))))

(defn build-population
  [population-size allele-set sequence-length fitness-function]
  (repeatedly population-size #(build-individual allele-set sequence-length fitness-function)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println (build-population 5 [1 0] 5 (partial apply +))))
