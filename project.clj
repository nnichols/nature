(defproject nature "0.0.1"
  :description "A simple genetic algorithms library for Clojure"
  :url "https://github.com/nnichols/nature"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0-alpha20"]
                 [bigml/sampling "3.2"]
                 [com.taoensso/tufte "2.0.1"]
                 [com.clojure-goes-fast/clj-memory-meter "0.1.2"]]
  :plugins [[lein-codox "0.10.5"]]
  :bikeshed {:long-lines false}
  :eastwood {:add-linters [:keyword-typos :unused-fn-args :unused-private-vars]}
  :codox {:output-path "docs/api"}
  :jvm-opts ["-Djdk.attach.allowAttachSelf"]
  :main ^:skip-aot nature.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
