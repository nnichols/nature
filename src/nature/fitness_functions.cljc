(ns nature.fitness-functions
  "Helper functions for common fitness function needs")

(defn binary-sequence-to-int
  "Convert `binary-seq`, a collection of binary values, to the positive integer it represents"
  [binary-seq]
  #?(:clj  (Integer/parseInt (apply str "" binary-seq) 2)
     :cljs (js/parseInt (apply str "" binary-seq) 2)))

(defn gray-binary-sequence-to-int
  "Convert `binary-seq`, a collection of binary values, to the positive integer it represents
   when considered by its Grey Coding: https://en.wikipedia.org/wiki/Gray_code"
  [binary-seq]
  (let [std-binary (binary-sequence-to-int binary-seq)]
    (loop [mask (bit-shift-right std-binary 1)
           val  std-binary]
      (if (= 0 mask)
        val
        (recur (bit-shift-right mask 1) (bit-xor val mask))))))

(defn int-to-decimal-mesh
  "Splits the continuous range from `lower-bound` to `upper-bound` into discrete `blocks`,
   than returns the decimal value of the `val-to-convert`th block"
  [val-to-convert lower-bound upper-bound blocks]
  (let [block-size (/ (- upper-bound lower-bound) blocks)
        offset     (* val-to-convert block-size)]
    (+ lower-bound offset)))
