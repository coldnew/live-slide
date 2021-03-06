(defproject lsd "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.9.0-alpha14"]
                 [org.clojure/clojurescript "1.9.293"]
                 [reagent "0.6.0"]
                 [re-frame "0.8.0"]
                 [re-frisk "0.3.1"]
                 [garden "1.3.2"]
                 [ns-tracker "0.3.0"]
                 [mount "0.1.11"]
                 [binaryage/oops "0.5.2"]
                 [cljsjs/react-bootstrap "0.30.6-0"]
                 [cljsjs/simplemde "1.11.2-0"]
                 [cljsjs/to-markdown "1.3.0-0"]]

  :plugins [[lein-cljsbuild "1.1.4"]
            [lein-garden "0.2.8"]]

  :min-lein-version "2.5.3"

  :source-paths ["src/clj"]

  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"
                                    "test/js"]

  :figwheel {:css-dirs ["resources/public/css"]}

  :profiles
  {:dev
   {:dependencies [[binaryage/devtools "0.8.2"]
                   [figwheel-sidecar "0.5.8"]
                   [com.cemerick/piggieback "0.2.1"]]
    :plugins      [[lein-figwheel "0.5.8"]
                   [lein-doo "0.1.7"]]
    ;; need to add dev source path here to get user.clj loaded
    :source-paths ["src/cljs" "src/clj" "dev"]
    ;; for CIDER
    ;; :plugins [[cider/cider-nrepl "0.12.0"]]
    :repl-options {; for nREPL dev you really need to limit output
                   :init (set! *print-length* 50)
                   :nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}}
   }

  :cljsbuild
  {:builds
   [{:id           "dev"
     :source-paths ["src/cljs" "src/clj"]
     :figwheel     {:on-jsload "lsd.core/mount-root"}
     :compiler     {:main                 lsd.core
                    :output-to            "resources/public/js/compiled/app.js"
                    :output-dir           "resources/public/js/compiled/out"
                    :asset-path           "js/compiled/out"
                    :source-map-timestamp true
                    :preloads             [devtools.preload]
                    :external-config      {:devtools/config {:features-to-install :all}}
                    }}

    {:id           "min"
     :source-paths ["src/cljs"]
     :compiler     {:main            lsd.core
                    :output-to       "resources/public/js/compiled/app.js"
                    :optimizations   :advanced
                    :closure-defines {goog.DEBUG false}
                    :pretty-print    false}}

    {:id           "test"
     :source-paths ["src/cljs" "test/cljs"]
     :compiler     {:main          lsd.runner
                    :output-to     "resources/public/js/compiled/test.js"
                    :output-dir    "resources/public/js/compiled/test/out"
                    :optimizations :none}}
    ]}

  )
