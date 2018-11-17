(ns nature.core
  (:require [nature.spec :as s])
  (:gen-class))

(defn uuid
  []
  (str (java.util.UUID/randomUUID)))

(defn build-individual
  "Create a generated individual"
  [allele-set sequence-length fitness-function]
  (let [genes (repeatedly sequence-length #(rand-nth allele-set))]
    (assoc {} :genetic-sequence genes
              :guid (uuid)
              :parents ["Initializer"]
              :age 0
              :fitness-score (fitness-function genes))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println (build-individual [1 0] 5 (partial apply +))))
