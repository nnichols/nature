(ns nature.genetic-operators-test
  (:require [clojure.test :refer :all]
            [clojure.spec.alpha :as csa]
            [nature.core :as core]
            [nature.spec :as s]
            [nature.genetic-operators :as go]
            [nature.population-presets :as pp]))

(deftest binary-operators-test
  (let [fitness-function (partial apply +)
        sample-population (core/build-population 2 ;; Crossover is currently just a binary operation
                                                 pp/binary-genome
                                                 (inc (rand-int 100))
                                                 fitness-function)]
    (testing "Ensure crossover creates two valid individuals"
      (is (csa/valid? ::s/population (go/crossover (first sample-population)
                                                   (second sample-population)
                                                   fitness-function))))
    (testing "Ensure fitness-based scanning creates two valid individuals"
      (is (csa/valid? ::s/population (go/fitness-based-scanning (first sample-population)
                                                                (second sample-population)
                                                                fitness-function))))))
