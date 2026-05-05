(ns duct.module.cljs-test
  (:require [clojure.test :refer [deftest is testing]]
            [duct.module.cljs]
            [integrant.core :as ig]))

(deftest module-test
  (testing "main config"
    (is (= {:duct.handler/file
            {:paths {"/cljs" {:root "target/cljs"}}}
            :duct.compiler.cljs.simple/build
            {:asset-path    "/cljs"
             :logger        (ig/refset :duct/logger)
             :main          'client.test
             :output-dir    "target/cljs"
             :output-to     "target/cljs/test.js" 
             :optimizations :advanced}}
           (ig/expand
            {:duct.module/cljs {:main 'client.test, :output-file "test.js"}}
            (ig/deprofile [:main])))))

  (testing "repl config"
    (is (= {:duct.handler/file
            {:paths {"/cljs" {:root "target/cljs"}}}
            :duct.compiler.cljs.simple/build
            {:asset-path    "/cljs"
             :logger        (ig/refset :duct/logger)
             :main          'client.test
             :output-dir    "target/cljs"
             :output-to     "target/cljs/test.js" 
             :optimizations :none
             :preloads      ['duct.client.repl.simple.preload]}
            :duct.compiler.cljs.simple/server
            {:logger (ig/refset :duct/logger)
             :build  (ig/ref :duct.compiler.cljs.simple/build)}}
           (ig/expand
            {:duct.module/cljs {:main 'client.test, :output-file "test.js"}}
            (ig/deprofile [:repl])))))

  (testing "custom paths"
    (is (= {:duct.handler/file
            {:paths {"/js" {:root "builds/js"}}}
            :duct.compiler.cljs.simple/build
            {:asset-path    "/js"
             :logger        (ig/refset :duct/logger)
             :main          'client.test
             :output-dir    "builds/js"
             :output-to     "builds/js/test.js" 
             :optimizations :none
             :preloads      ['duct.client.repl.simple.preload]}
            :duct.compiler.cljs.simple/server
            {:logger (ig/refset :duct/logger)
             :build  (ig/ref :duct.compiler.cljs.simple/build)}}
           (ig/expand
             {:duct.module/cljs {:main        'client.test
                                 :output-file "test.js"
                                 :output-dir  "builds/js"
                                 :asset-path  "/js"}}
             (ig/deprofile [:repl]))))))
