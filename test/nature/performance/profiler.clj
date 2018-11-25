(ns nature.performance.profiler
  (:require [nature.core :as core]
            [taoensso.tufte :as tufte :refer (defnp p profiled profile)]))

(tufte/add-basic-println-handler! {})

(profile
 {}
 (dotimes [_ 10]
   (p :generate-sequence (core/generate-sequence [1 0] 500))
   (p :build-individual (core/build-individual [1 0] 500 (partial apply +)))
   (p :build-population (core/build-population 500 [1 0] 500 (partial apply +)))))

