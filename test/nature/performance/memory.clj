(ns nature.performance.memory
  (:require [nature.core :as core]
            [clj-memory-meter.core :as mm]
            [clojure.test :refer :all]))

(deftest generate-sequence-footprint
  (testing "Ensure memory usage grows sustainably, and report usage to user"
    (let [length-b-10  (mm/measure (doall (core/generate-sequence [1 0] 10))) ; doall  to force sequences to actually be built
          length-b-50  (mm/measure (doall (core/generate-sequence [1 0] 50)))
          length-b-100 (mm/measure (doall (core/generate-sequence [1 0] 100)))
          length-b-500 (mm/measure (doall (core/generate-sequence [1 0] 500)))
          length-s-10  (mm/measure (doall (core/generate-sequence ["1" "0"] 10)))
          length-s-50  (mm/measure (doall (core/generate-sequence ["1" "0"] 50)))
          length-s-100 (mm/measure (doall (core/generate-sequence ["1" "0"] 100)))
          length-s-500 (mm/measure (doall (core/generate-sequence ["1" "0"] 500)))]
      (is (nil? (println "core/generate-sequence memory footprint (Binary), Length 10: " length-b-10)))
      (is (nil? (println "core/generate-sequence memory footprint (Binary), Length 50: " length-b-50)))
      (is (nil? (println "core/generate-sequence memory footprint (Binary), Length 100: " length-b-100)))
      (is (nil? (println "core/generate-sequence memory footprint (Binary), Length 500: " length-b-500)))
      (is (nil? (println "-----------------------")))
      (is (nil? (println "core/generate-sequence memory footprint (Singleton String), Length 10: " length-b-10)))
      (is (nil? (println "core/generate-sequence memory footprint (Singleton String), Length 50: " length-b-50)))
      (is (nil? (println "core/generate-sequence memory footprint (Singleton String), Length 100: " length-b-100)))
      (is (nil? (println "core/generate-sequence memory footprint (Singleton String), Length 500: " length-b-500)))
      (is (nil? (println "-----------------------"))))))

(deftest build-individual-footprint
  (testing "Ensure memory usage grows sustainably, and report usage to user"
    (let [length-10  (mm/measure (doall (core/build-individual [1 0] 10  (partial apply +))))
          length-50  (mm/measure (doall (core/build-individual [1 0] 50  (partial apply +))))
          length-100 (mm/measure (doall (core/build-individual [1 0] 100 (partial apply +))))
          length-500 (mm/measure (doall (core/build-individual [1 0] 500 (partial apply +))))]
      (is (nil? (println "core/build-individual memory footprint (Binary), Length 10: " length-10)))
      (is (nil? (println "core/build-individual memory footprint (Binary), Length 50: " length-50)))
      (is (nil? (println "core/build-individual memory footprint (Binary), Length 100: " length-100)))
      (is (nil? (println "core/build-individual memory footprint (Binary), Length 500: " length-500)))
      (is (nil? (println "-----------------------"))))))

(deftest build-population-footprint
  (testing "Ensure memory usage grows sustainably, and report usage to user"
    (let [length-10  (mm/measure (doall (core/build-population 10  [1 0] 500 (partial apply +))))
          length-50  (mm/measure (doall (core/build-population 50  [1 0] 500 (partial apply +))))
          length-100 (mm/measure (doall (core/build-population 100 [1 0] 500 (partial apply +))))
          length-500 (mm/measure (doall (core/build-population 500 [1 0] 500 (partial apply +))))]
      (is (nil? (println "core/build-population memory footprint (Binary), Size 10: " length-10)))
      (is (nil? (println "core/build-population memory footprint (Binary), Size 50: " length-50)))
      (is (nil? (println "core/build-population memory footprint (Binary), Size 100: " length-100)))
      (is (nil? (println "core/build-population memory footprint (Binary), Size 500: " length-500)))
      (is (nil? (println "-----------------------"))))))
