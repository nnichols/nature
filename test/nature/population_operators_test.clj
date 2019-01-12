(ns nature.population-operators-test
  (:require [clojure.test :refer :all]
            [clojure.spec.alpha :as csa]
            [nature.core :as core]
            [nature.spec :as s]
            [nature.genetic-operators :as go]
            [nature.population-operators :as po]
            [nature.population-presets :as pp]))

(deftest advance-generation-test
  (testing "Check that a new generation can be created from a prior generation"
    (let [fitness-function (partial apply +)
          sample-population (core/build-population 20
                                                   pp/binary-genome
                                                   20
                                                   fitness-function)
          new-generation (po/advance-generation sample-population
                                                [(partial go/fitness-based-scanning fitness-function)
                                                 (partial go/crossover fitness-function)]
                                                [(partial go/mutation-operator fitness-function pp/binary-genome 1)])]
      (is (csa/valid? ::s/population new-generation))
      (is (= 20 (count new-generation))))))

(deftest weighted-selection-of-population-test
  (testing "Check that selections of a population still conform to the spec, and are distinct if required"
    (let [population (core/build-population (inc (rand-int 100))
                                            pp/binary-genome
                                            (inc (rand-int 100))
                                            (partial apply +))
          selected-population-2-arg (po/weighted-selection-of-population population 10)
          selected-population-3-arg (po/weighted-selection-of-population population 10 true)
          selected-partition (po/weighted-selection-of-population population 10 false)]
      (is (csa/valid? ::s/population selected-population-2-arg))
      (is (csa/valid? ::s/population selected-population-3-arg))
      (is (csa/valid? ::s/population selected-partition))
      (is (= selected-partition (distinct selected-partition))))))

(deftest keep-elite-test
  (testing "Check that keep-elite preserves the top `n` individuals"
    (let [sample-population (core/build-population 50
                                                   pp/binary-genome
                                                   50
                                                   (partial apply +))
          elite-population  (po/keep-elite sample-population 10)]
      (is (csa/valid? ::s/population elite-population))
      (is (= 10 (count elite-population)))
      (is (every? #(= 1 (:age %)) elite-population)))))
