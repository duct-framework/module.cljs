(ns duct.module.cljs-test
  (:require [clojure.test :refer [deftest is testing]]
            [duct.module.cljs]
            [integrant.core :as ig]))

(deftest module-test
  (testing "main config"
    (is (= {:duct.handler/file
            {:paths {"/cljs" {:root "target/cljs"}}}
            :duct.compiler.cljs.shadow/release
            {:build {:target :browser
                     :output-dir "target/cljs"
                     :asset-path "/cljs"
                     :modules {:main {:init-fn 'client/test}}}}}
           (ig/expand
            {:duct.module/cljs {:builds {:main 'client/test}}}
            (ig/deprofile [:main])))))

  (testing "repl config"
    (is (= {:duct.handler/file
            {:paths {"/cljs" {:root "target/cljs"}}}
            :duct.compiler.cljs.shadow/server
            {:build {:target :browser
                     :output-dir "target/cljs"
                     :asset-path "/cljs"
                     :modules {:main {:init-fn 'client/test}}}}}
           (ig/expand
            {:duct.module/cljs {:builds {:main 'client/test}}}
            (ig/deprofile [:repl])))))

  (testing "custom paths"
    (is (= {:duct.handler/file
            {:paths {"/js" {:root "builds/js"}}}
            :duct.compiler.cljs.shadow/server
            {:build {:target :browser
                     :output-dir "builds/js"
                     :asset-path "/js"
                     :modules {:main {:init-fn 'client/test}}}}}
           (ig/expand
            {:duct.module/cljs
             {:builds {:main 'client/test}
              :output-dir "builds/js"
              :asset-path "/js"}}
            (ig/deprofile [:repl]))))))
