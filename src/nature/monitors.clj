(ns nature.monitors
  "Helper functions to inspect generations and return run-time information, statistics, etc.")

(defn apply-monitors
  "Apply each function in `monitors` against `population` and `current-generation`"
  [monitors population current-generation]
  (let [fns (apply juxt monitors)]
    (do (fns population current-generation))))

(defn print-best-solution
  "Finds the individual with the highest fitness in `population`, and prints it to std-out"
  [population current-generation]
  (println (first (sort-by :fitness-score #(> %1 %2) population))))
