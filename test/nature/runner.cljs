(ns nature.runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [nature.core-test]
            [nature.fitness-functions-test]
            [nature.genetic-operators-test]
            [nature.initialization-operators-test]
            [nature.monitors-test]
            [nature.population-operators-test]))


(doo-tests 'nature.core-test
           'nature.fitness-functions-test
           'nature.genetic-operators-test
           'nature.initialization-operators-test
           'nature.monitors-test
           'nature.population-operators-test)
