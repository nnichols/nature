(ns nature.core-test
  (:require [clojure.test :refer :all]
            [clojure.spec.alpha :as csa]
            [nature.core :as core]
            [nature.spec :as s]
            [nature.population-presets :as pp]))

(deftest uuid-test
  (testing "Ensure uuids are sensible"
    (is (string? (core/uuid)))
    (is (= 36 (count (core/uuid))))))

(deftest integer-genome-test
  (testing "Sanity checking integer genome generation"
    (let [test-cap 65535
          test-bot 1]
      (is (every? #(< 0 % 11) (pp/integer-genome)))
      (is (every? #(< 0 % (inc test-cap)) (pp/integer-genome test-cap)))
      (is (every? #(< 0 % (inc test-cap)) (pp/integer-genome test-cap test-bot)))
      (is (every? #(< 0 % (inc test-cap)) (pp/integer-genome test-cap test-bot 2)))
      (is (every? odd? (pp/integer-genome test-cap test-bot 2)))
      (is (every? even? (pp/integer-genome test-cap (inc test-bot) 2))))))
