(ns nature.spec
  "Common specs/api checks for core nature functions"
  (:require [clojure.spec.alpha :as s]))

(defn not-empty?
  "A predicate version of not-empty, because it's a sensible feature"
  [coll]
  (boolean (seq coll)))

(s/def ::genetic-sequence
  (s/and coll?
         not-empty?))

(s/def ::guid string?)

(s/def ::parents
  (s/and coll?
         not-empty?))

(s/def ::age integer?)

(s/def ::fitness-score number?)

(s/def ::individual
  (s/keys :req-un [::genetic-sequence
                   ::guid
                   ::parents
                   ::age
                   ::fitness-score]))

(s/def ::population
  (s/and #(s/coll-of (s/valid? ::individual %))
         not-empty?))
