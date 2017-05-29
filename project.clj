(defproject spread3-web-ui "0.0.1"
  :dependencies [;; clj
                 [org.clojure/clojure "1.8.0"]
                 [org.clojure/tools.logging "0.3.1"]
                 [org.clojure/data.codec "0.1.0"]
                 [ring "1.5.1"]
                 [ring/ring-core "1.5.1"]
                 [ring/ring-jetty-adapter "1.5.1"]
                 [ring-middleware-format "0.7.2"]
                 [ring-cors "0.1.9"]
                 [compojure "1.5.2"]
                 [clj-json "0.5.3"]
                 ;; cljs
                 [org.clojure/clojurescript "1.9.495"]
                 [reagent "0.6.1"]
                 [re-frame "0.9.2"]
                 [com.cognitect/transit-cljs "0.8.239"]
                 [cljs-ajax "0.5.8"]
                 [re-frisk "0.3.2"]
                 [com.cemerick/piggieback "0.2.1"]
                 [figwheel-sidecar "0.5.9"]]
  ;; clj
  :main spread-ui.system
  :repl-options {:init-ns ^:skip-aot user}
  :source-paths ["src/clj"] 
  :resource-paths ["resources"]
  :profiles {:dev {:source-paths ["dev"]
                   :dependencies [[org.clojure/tools.namespace "0.2.4"]
                                  [org.clojure/tools.nrepl "0.2.12"]
                                  [com.cemerick/pomegranate "0.2.0"]
                                  [spyscope "0.1.3"]
                                  [criterium "0.4.1"]
                                  [org.clojure/java.classpath "0.2.0"]
                                  [ring/ring-mock "0.3.0"]
                                  [com.billpiel/sayid "0.0.14"]]
                   :plugins [[lein-kibit "0.0.8"]
                             [cider/cider-nrepl "0.14.0"]
                             [com.billpiel/sayid "0.0.14"]]}
             :uberjar {:main spread-ui.system
                       :source-paths ["src/clj"]
                       :aot :all
                       :auto-clean false
                       :uberjar-name "spread-ui.jar"
                       :env {:clj-env :production}}
             :test {:source-paths ["src/clj" "test/clj"]
                    :dependencies [[ring/ring-mock "0.3.0"]]
                    :resource-paths ["test/resources"]
                    :env {:clj-env :test
                          :server-config {:host "localhost" :port 8080}}}}
  ;; cljs
  :plugins [[lein-cljsbuild "1.1.5"]
            [lein-figwheel "0.5.9" :exclusions [cider/cider-nrepl]]
            [lein-npm "0.6.2"]
            [lein-doo "0.1.7"]]
  :figwheel {:css-dirs ["resources/public/styles"]
             :server-port 8080}
  :npm {:dependencies [[karma "1.5.0"]
                       [karma-cli "1.0.1"]
                       [karma-cljs-test "0.1.0"]
                       [karma-chrome-launcher "2.0.0"]]}
  :doo {:paths {:karma "./node_modules/karma/bin/karma"}}
  :cljsbuild {:builds [{:id "dev"
                        :source-paths ["src/cljs"]
                        :figwheel {:on-jsload "spread-ui.core/mount-root"}
                        :compiler {:main spread-ui.core
                                   :output-to "resources/public/js/compiled/app.js"
                                   :output-dir "resources/public/js/compiled/out"
                                   :asset-path "js/compiled/out"
                                   :closure-defines {goog.DEBUG true}
                                   :optimizations :none}}
                       {:id "test"
                        :source-paths ["src/cljs" "test/cljs"]
                        :notify-command ["npm install"]
                        :compiler {:output-to "resources/public/js/compiled/test/browser_tests.js"
                                   :output-dir "resources/public/js/compiled/test/out"
                                   :main spread-ui.browser
                                   :optimizations :none}}
                       {:id "min"
                        :source-paths ["src/cljs"]
                        :compiler {:main spread-ui.core
                                   :output-to "resources/public/js/compiled/app.js"
                                   :closure-defines {goog.DEBUG false}
                                   :optimizations :advanced
                                   :pretty-print false}}]}
  :aliases {"deploy" ["do" "clean" ["cljsbuild" "once" "min"] ["uberjar"]]}
  ;; shared
  :clean-targets ^{:protect false} ["resources/public/js/compiled"
                                    "target"
                                    "test/js"])
