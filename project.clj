(defproject nature "1.0.0"
            :description "A simple genetic algorithms library for Clojure(Script)"
            :url "https://github.com/nnichols/nature"
            :license {:name "Eclipse Public License"
                      :url "http://www.eclipse.org/legal/epl-v10.html"}
            :dependencies [[org.clojure/clojure "1.11.1"]
                           [org.clojure/clojurescript "1.11.60" :scope "provided"]
                           [cljx-sampling "0.1.0"]]

            :plugins [[com.jakemccrary/lein-test-refresh "0.25.0"]
                      [lein-cljsbuild "1.1.7"]
                      [lein-figwheel "0.5.14"]]

            :aliases {"test-build" ["do" "clean" ["cljsbuild" "once" "test"] ["doo" "once"]]}

            :cljsbuild {:builds
                        [{:id "test"
                          :source-paths ["src" "test"]
                          :compiler {:main "nature.runner"
                                     :output-to "target/test/app.js"
                                     :output-dir "target/test/js/compiled/out"
                                     :optimizations :none
                                     :parallel-build true}}]}

            :doo {:build "test"
                  :alias {:default [:chrome-headless-no-sandbox]}
                  :paths {:karma "./node_modules/karma/bin/karma"}
                  :karma {:launchers {:chrome-headless-no-sandbox {:plugin "karma-chrome-launcher"
                                                                   :name   "ChromeHeadlessNoSandbox"}}
                          :config    {"captureTimeout"             210000
                                      "browserDisconnectTolerance" 3
                                      "browserDisconnectTimeout"   210000
                                      "browserNoActivityTimeout"   210000
                                      "customLaunchers"            {"ChromeHeadlessNoSandbox"
                                                                    {"base"  "ChromeHeadless"
                                                                     "flags" ["--no-sandbox" "--disable-dev-shm-usage"]}}}}}

            :min-lein-version "2.5.3"
            :target-path "target/%s"
            :profiles {:uberjar {:aot :all}
                       :dev {:dependencies [[doo "0.1.11"]]
                             :plugins      [[lein-doo "0.1.10"]]}})
