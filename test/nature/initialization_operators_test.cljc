(ns nature.initialization-operators-test
  (:require [clojure.spec.alpha :as csa]
            [nature.spec :as s]
            [nature.initialization-operators :as io]
            [nature.population-presets :as pp]
            #? (:clj  [clojure.test :refer [deftest is testing]])
            #? (:cljs [cljs.test    :refer-macros [deftest is testing]])))

(deftest uuid-test
  (testing "Ensure uuids are sensible"
    (is (string? (io/uuid)))
    (is (= 36 (count (io/uuid))))))

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

(deftest not-empty-test
  (testing "Sanity checking not-empty?"
    (is (not (s/not-empty? '())))
    (is (not (s/not-empty? [])))
    (is (not (s/not-empty? {})))
    (is (s/not-empty? '(1 2 3)))
    (is (s/not-empty? pp/binary-genome))
    (is (s/not-empty? '('a 'b)))
    (is (s/not-empty? #{:a 2 :b "123" :c true}))))

(deftest build-individual-test
  (testing "Check that each key-value pair of a generated individual conforms to the spec"
    (let [individual (io/build-individual [0 1 0 1 0 1 0 1 0 1] (partial apply +))]
      (is (csa/valid? ::s/genetic-sequence (:genetic-sequence individual)))
      (is (csa/valid? ::s/guid (:guid individual)))
      (is (csa/valid? ::s/parents (:parents individual)))
      (is (csa/valid? ::s/age (:age individual)))
      (is (csa/valid? ::s/fitness-score (:fitness-score individual)))
      (is (csa/valid? ::s/individual individual))
      (is (not (csa/valid? ::s/individual '())))
      (is (not (csa/valid? ::s/individual {})))
      (is (not (csa/valid? ::s/individual (dissoc individual :genetic-sequence))))
      (is (not (csa/valid? ::s/individual (dissoc individual :guid))))
      (is (not (csa/valid? ::s/individual (dissoc individual :parents))))
      (is (not (csa/valid? ::s/individual (dissoc individual :age))))
      (is (not (csa/valid? ::s/individual (dissoc individual :fitness-score)))))))

(deftest build-population-test
  (testing "Check that each key-value pair of all generated individuals in a population conforms to the spec"
    (let [population (io/build-population (inc (rand-int 100))
                                          pp/binary-genome
                                          (inc (rand-int 100))
                                          (partial apply +))]
      (is (csa/valid? ::s/population population)))))
