(defproject nature "0.1.1"
  :description "A simple genetic algorithms library for Clojure"
  :url "https://github.com/nnichols/nature"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [bigml/sampling "3.2"]]
  :bikeshed {:long-lines false}
  :eastwood {:add-linters [:unused-fn-args :unused-private-vars]}
  :main ^:skip-aot nature.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
