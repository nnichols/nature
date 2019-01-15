(ns nature.genetic-operators-test
  (:require [clojure.test :refer :all]
            [clojure.spec.alpha :as csa]
            [nature.spec :as s]
            [nature.genetic-operators :as go]
            [nature.initialization-operators :as io]
            [nature.population-presets :as pp]))

(deftest binary-operators-*-test
  (let [sample-population (io/build-population 2
                                               pp/binary-genome
                                               (inc (rand-int 100))
                                               pp/sum-alleles)]
    (testing "Ensure crossover creates two valid individuals"
      (is (csa/valid? ::s/population (go/crossover* pp/sum-alleles sample-population))))
    (testing "Ensure fitness-based scanning creates two valid individuals"
      (is (csa/valid? ::s/population (go/fitness-based-scanning* pp/sum-alleles sample-population))))))

(deftest fitness-based-scanning-allele-test
  (testing "Test extrema of percents passed to allele selection"
    (let [allele-1 0
          allele-2 1]
      (is (= (go/fitness-based-scanning-allele allele-1 allele-2 0) allele-1))
      (is (= (go/fitness-based-scanning-allele allele-1 allele-2 100) allele-2)))))

(deftest fitness-based-scanning-genome-test
  (testing "Test extrema of percents passed to genome selection"
    (let [genome-1 [10 11 12 13 14 15 16 17 18 19]
          genome-2 [20 21 22 23 24 25 26 27 28 29]]
      (is (= (go/fitness-based-scanning-genome genome-1 genome-2 0) genome-1))
      (is (= (go/fitness-based-scanning-genome genome-1 genome-2 100) genome-2)))))

(deftest mutation-operator-test
  (testing "Test extrema of percents passed to mutation"
    (let [individual (io/build-individual [0 0 0 0 0 0 0 0 0 0] pp/sum-alleles)
          no-mutation (go/mutation-operator pp/sum-alleles [1] 0  individual)
          all-mutation (go/mutation-operator pp/sum-alleles [1] 100 individual)]
      (is (csa/valid? ::s/individual no-mutation))
      (is (csa/valid? ::s/individual all-mutation))
      (is (s/not-empty? (:genetic-sequence no-mutation)))
      (is (s/not-empty? (:genetic-sequence all-mutation)))
      (is (every? #(= 0 %) (:genetic-sequence no-mutation)))
      (is (every? #(= 1 %) (:genetic-sequence all-mutation)))
      (is (= 0 (:age no-mutation) (:age all-mutation) (:age individual)))
      (is (= (:parents no-mutation) (:parents all-mutation))))))

(deftest no-op-test
  (testing "Ensure no-op returns the same individual with only the age modified"
    (let [original (io/build-individual pp/binary-genome pp/sum-alleles)
          modified (first (go/no-op pp/sum-alleles (vector original)))]
      (is (csa/valid? ::s/individual modified))
      (is (= (:guid original) (:guid modified)))
      (is (= (:parents original) (:parents modified)))
      (is (= (:fitness-score original) (:fitness-score modified)))
      (is (= (inc (:age original)) (:age modified)))
      (is (= (:genetic-sequence original) (:genetic-sequence modified))))))
