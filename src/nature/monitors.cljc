(ns nature.monitors
  "Helper functions to inspect generations and return run-time information, statistics, etc.")

(defn apply-monitors
  "Apply each function in `monitors` against `population` and `current-generation`"
  [monitors population current-generation]
  (let [fns (apply juxt monitors)]
    (fns population current-generation)))

(defn mk-monitor
  "Apply `monitor-fn` to the `population` and `current-generation`, and print the result.
   If a `format-fn` has been supplied, modify the output before printing.
   This is useful for separating the monitor code into a separate testable function."
  ([monitor-fn population current-generation]
   (println (monitor-fn population current-generation)))

  ([monitor-fn population current-generation format-fn]
   (println (format-fn (monitor-fn population current-generation)))))

(defn print-best-solution*
  "Finds the individual with the highest fitness in `population`"
  [population _current-generation]
  (first (sort-by :fitness-score #(> %1 %2) population)))

(defn print-best-solution
  "Finds the individual with the highest fitness in `population`, and prints it to std-out"
  [population current-generation]
  (mk-monitor print-best-solution* population current-generation))

(defn print-worst-solution*
  "Finds the individual with the lowest fitness in `population`"
  [population _current-generation]
  (first (sort-by :fitness-score #(> %2 %1) population)))

(defn print-worst-solution
  "Finds the individual with the lowest fitness in `population`, and prints it to std-out"
  [population current-generation]
  (mk-monitor print-worst-solution* population current-generation))

(defn print-solution-frequencies*
  "Finds how frequently each genetic sequence is repeated across the `population`"
  [population _current-generation]
  (frequencies (map :genetic-sequence population)))

(defn print-solution-frequencies
  "Finds how frequently each genetic sequence is repeated across the `population`, and prints it to std-out"
  [population current-generation]
  (mk-monitor print-solution-frequencies* population current-generation))

(defn print-fitness-score-frequencies*
  "Finds how frequently each fitness score is repeated across the `population`"
  [population _current-generation]
  (frequencies (map :fitness-score population)))

(defn print-fitness-score-frequencies
  "Finds how frequently each fitness score is repeated across the `population`, and prints it to std-out"
  [population current-generation]
  (mk-monitor print-fitness-score-frequencies* population current-generation))
