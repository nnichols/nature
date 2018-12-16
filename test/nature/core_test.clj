(ns nature.core-test
  (:require [clojure.test :refer :all]
            [clojure.spec.alpha :as csa]
            [nature.core :as core]
            [nature.spec :as s]))

(deftest uuid-test
  (testing "Ensure uuids are sensible"
    (is (string? (core/uuid)))
    (is (= 36 (count (core/uuid))))))
