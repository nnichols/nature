(ns nature.fitness-functions-test
  (:require [clojure.test :refer :all]
            [nature.fitness-functions :as ff]))

(deftest binary-sequence-to-int-test
  (testing "Ensure binary->integer parsing works for various genomes, and fails on bad data"
    (is (= 0 (ff/binary-sequence-to-int [0 0 0 0 0 0 0 0 0 0])))
    (is (= 1023 (ff/binary-sequence-to-int [1 1 1 1 1 1 1 1 1 1])))
    (is (thrown? Exception (ff/binary-sequence-to-int [])))
    (is (thrown? Exception (ff/binary-sequence-to-int [2 1 1 1 1 1 1 1 1 1])))))
