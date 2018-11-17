(ns nature.core-test
  (:require [clojure.test :refer :all]
            [clojure.spec.alpha :as csa]
            [nature.core :as core]
            [nature.spec :as s]))

(deftest build-individual-test
  (testing "Check that each key-value pair of a generated individual conforms to the spec"
    (let [individual (core/build-individual [0 1] 10 (partial apply +))]
      (is (csa/valid? ::s/genetic-sequence (:genetic-sequence individual)))
      (is (csa/valid? ::s/guid (:guid individual)))
      (is (csa/valid? ::s/parents (:parents individual)))
      (is (csa/valid? ::s/age (:age individual)))
      (is (csa/valid? ::s/fitness-score (:fitness-score individual)))
      (is (csa/valid? ::s/individual individual)))))
