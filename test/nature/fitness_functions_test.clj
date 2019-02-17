(ns nature.fitness-functions-test
  (:require [clojure.test :refer :all]
            [nature.fitness-functions :as ff]))

(deftest binary-sequence-to-int-test
  (testing "Ensure binary->integer parsing works for various genomes, and fails on bad data"
    (is (= 0 (ff/binary-sequence-to-int [0 0 0 0 0 0 0 0 0 0])))
    (is (= 1023 (ff/binary-sequence-to-int [1 1 1 1 1 1 1 1 1 1])))
    (is (thrown? Exception (ff/binary-sequence-to-int [])))
    (is (thrown? Exception (ff/binary-sequence-to-int [2 1 1 1 1 1 1 1 1 1])))))

(deftest binary-sequence-to-int-test
  (testing "Ensure binary->integer parsing works for various genomes in Gray code, and fails on bad data"
    (is (= 0 (ff/gray-binary-sequence-to-int [0 0 0 0 0 0 0 0 0 0])))
    (is (= 1 (ff/gray-binary-sequence-to-int [0 0 0 0 0 0 0 0 0 1])))
    (is (= 4 (ff/gray-binary-sequence-to-int [0 0 0 0 0 0 0 1 1 0])))
    (is (= 15 (ff/gray-binary-sequence-to-int [0 0 0 0 0 0 1 0 0 0])))
    (is (thrown? Exception (ff/gray-binary-sequence-to-int [])))
    (is (thrown? Exception (ff/gray-binary-sequence-to-int [2 1 1 1 1 1 1 1 1 1])))))

(deftest int-to-decimal-mesh-test
  (testing "Ensure continuous ranges can be broken down into even-blocks and offsets may be applied"
    (let [random-fn (fn [] (inc (rand-int 1000000)))
          test-lower-bound (random-fn)
          test-upper-bound (+ test-lower-bound (random-fn))
          test-blocks (random-fn)
          test-offset (rand-int test-blocks)]
      (is (= test-lower-bound  (ff/int-to-decimal-mesh 0 test-lower-bound test-upper-bound test-blocks)))
      (is (<= test-lower-bound (ff/int-to-decimal-mesh test-offset test-lower-bound test-upper-bound test-blocks)))
      (is (>= test-upper-bound (ff/int-to-decimal-mesh test-offset test-lower-bound test-upper-bound test-blocks))))))
