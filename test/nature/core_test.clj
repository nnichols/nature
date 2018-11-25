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
      (is (csa/valid? ::s/individual individual))
      (is (not (csa/valid? ::s/individual (dissoc individual :genetic-sequence))))
      (is (not (csa/valid? ::s/individual (dissoc individual :guid))))
      (is (not (csa/valid? ::s/individual (dissoc individual :parents))))
      (is (not (csa/valid? ::s/individual (dissoc individual :age))))
      (is (not (csa/valid? ::s/individual (dissoc individual :fitness-score)))))))

(deftest build-population-test
  (testing "Check that each key-value pair of all generated individuals in a population conforms to the spec"
    (let [population (core/build-population 20 [0 1] 10 (partial apply +))]
      (is (csa/valid? ::s/population population)))))

(deftest weighted-selection-of-population-test
  (testing "Check that selections of a population still conform to the spec, and are distinct if required"
    (let [population (core/build-population 20 [0 1] 10 (partial apply +))
          selected-population-2-arg (core/weighted-selection-of-population population 10)
          selected-population-3-arg (core/weighted-selection-of-population population 10 true)
          selected-partition (core/weighted-selection-of-population population 10 false)]
      (is (csa/valid? ::s/population selected-population-2-arg))
      (is (csa/valid? ::s/population selected-population-3-arg))
      (is (csa/valid? ::s/population selected-partition))
      (is (= selected-partition (distinct selected-partition))))))
