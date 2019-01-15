(ns nature.performance.profiler
  (:require [nature.initialization-operators :as io]
            [nature.population-presets :as pp]
            [taoensso.tufte :as tufte :refer (defnp p profiled profile)]))

(tufte/add-basic-println-handler! {})

(profile
 {}
 (dotimes [_ 10]
   (p :generate-sequence (io/generate-sequence [1 0] 500))
   (p :build-individual (io/build-individual (io/generate-sequence [1 0] 500) (partial apply +)))
   (p :build-population (io/build-population 500 [1 0] 500 (partial apply +)))))
