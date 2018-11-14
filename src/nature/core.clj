(ns nature.core
  (:require [clojure.spec.alpha :as s]
            [clj-uuid :as uuid])
  (:gen-class))

(def data-model
  {:genetic-sequence [0 1 0 1 1 1]
   :guid "12345"
   :age 1
   :fitness-score 0.1M})

(s/def ::genetic-sequence
  (s/and vector?
         #(not (empty? %))))

(s/def ::guid string?)

(s/def ::age integer?)

(s/def ::fitness-score decimal?)

(s/def ::individual
  (s/keys :req-un [::genetic-sequence
                   ::guid
                   ::age
                   ::fitness-score]))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println (s/valid? ::individual data-model)))
