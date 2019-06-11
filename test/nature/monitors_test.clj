(ns nature.monitors-test
  (:require [clojure.test :refer :all]
            [clojure.spec.alpha :as csa]
            [nature.spec :as s]
            [nature.monitors :as mo]
            [nature.initialization-operators :as io]
            [nature.population-presets :as pp]))

(deftest best-worst-solution-monitor-test
  (let [sample-population (io/build-population 200
                                               pp/binary-genome
                                               (inc (rand-int 100))
                                               pp/sum-alleles)
        best-solution (mo/print-best-solution* sample-population 0)
        worst-solution (mo/print-worst-solution* sample-population 0)]
    (testing "Ensure best/worst solutions are unique, valid, and meet monitor criteria"
      (is (csa/valid? ::s/individual best-solution))
      (is (csa/valid? ::s/individual worst-solution))
      (is (not= (:guid best-solution) (:guid worst-solution)))
      (is (>= (:fitness-score best-solution) (:fitness-score worst-solution))))))

(deftest frequencies-monitors-test
  (let [sample-population (io/build-population 200
                                               pp/binary-genome
                                               (inc (rand-int 100))
                                               pp/sum-alleles)
        fitness-frequencies (mo/print-fitness-score-frequencies* sample-population 0)
        solution-frequencies (mo/print-solution-frequencies* sample-population 0)]
    (testing "Ensure frequency monitors are not empty and contain valid values"
      (is (not-empty fitness-frequencies))
      (is (not-empty solution-frequencies))
      (is (every? #(csa/valid? ::s/fitness-score %) (keys fitness-frequencies)))
      (is (every? #(csa/valid? ::s/genetic-sequence %) (keys solution-frequencies))))))
