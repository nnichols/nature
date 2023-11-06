(ns nature.core
  (:require [nature.initialization-operators :as io]
            [nature.population-operators :as po]
            [nature.monitors :as monitors]))

(defn evolve
  "Create and evolve a population under the specified conditions until a termination criteria is reached
  `allele-set` is a collection of legal genome values
  `genome-length` is the enforced size of each genetic sequence
  `population-size` is the enforced number of individuals that will be created
  `generations` is the number of iterations the algorithm will cycle through
  `fitness-function` is a partial function accepting generated sequences to evaluate solution qualities
  `binary-operators` is a collection of partial functions accepting and returning 1 or more individuals
  `unary-operators` is a collection of partial functions accepting and returning exactly 1 individual
  `options` an optional map of pre-specified keywords to values that further tune the behavior of nature.
      Current examples follow:
      `:carry-over` an integer representing the top n individuals to be carried over between each generation. Default is 1
      `:solutions` an integer representing the top n individuals to return after evolution completes. Default is 1
      `:monitors` a sequence of functions, assumed to be side-effectful, to be executed against `population` and `current-genration` for run-time stats. Default is nil"
  ([allele-set genome-length population-size generations fitness-function binary-operators unary-operators]
   (evolve allele-set genome-length population-size generations fitness-function binary-operators unary-operators {:solutions 1, :carry-over 1}))

  ([allele-set genome-length population-size generations fitness-function binary-operators unary-operators options] ;; TODO - Curry the genetic operators one more level, so the fitness-function can be pressed in
   {:pre [(and (every? coll? [allele-set binary-operators unary-operators])
               (every? int? [genome-length population-size generations])
               (fn? fitness-function))]}
   (let [solutions (max 1 (:solutions options))
         carry-over (max 1 (:carry-over options))
         monitors (:monitors options)
         sequence-generator-function (:generator options)]
     (loop [population (if (some? sequence-generator-function)
                         (io/build-population population-size sequence-generator-function fitness-function) 
                         (io/build-population population-size allele-set genome-length fitness-function))
            current-generation 0]
       (when monitors (monitors/apply-monitors monitors population current-generation))
       (if (>= current-generation generations)
         (take solutions (sort-by :fitness-score #(> %1 %2) population))
         (recur (po/advance-generation population population-size binary-operators unary-operators {:carry-over carry-over}) (inc current-generation)))))))
