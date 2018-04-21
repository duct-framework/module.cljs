(ns duct.module.cljs-test
  (:require [clojure.java.io :as io]
            [clojure.test :refer :all]
            [duct.core :as core]
            [duct.module.cljs :as cljs]
            [integrant.core :as ig]))

(core/load-hierarchy)

(def service-worker-base-config
  {:duct.core/project-ns 'foo
   :duct.module/cljs     {:main 'foo.client
                          :sw-main 'foo.sw}})

(def base-config
  {:duct.core/project-ns 'foo
   :duct.module/cljs     {:main 'foo.client}})

(defn- absolute-path [relative-path]
  (.getAbsolutePath (io/file relative-path)))

(deftest service-worker-module-test
  (testing "production config"
    (is (= (core/prep service-worker-base-config)
           (merge service-worker-base-config
                  {:duct.compiler/cljs
                   {:builds
                    [{:id "sw"
                      :source-paths ["src"]
                      :build-options
                      {:main 'foo.sw
                       :output-to   (absolute-path "target/resources/foo/public/sw.js")
                       :output-dir (absolute-path "target/resources/foo/public/sw")
                       :asset-path "/sw"
                       :closure-defines {'goog.DEBUG false}
                       :verbose true
                       :infer-externs true
                       :language-in :es6
                       :rewrite-polyfills true
                       :optimizations :advanced
                       :target :webworker}}
                     {:source-paths ["src"]
                      :build-options
                      {:main       'foo.client
                       :output-to  (absolute-path "target/resources/foo/public/js/main.js")
                       :output-dir (absolute-path "target/resources/foo/public/js")
                       :asset-path "/js"
                       :closure-defines {'goog.DEBUG false}
                       :verbose    true
                       :optimizations :advanced}}]}}))))

  (testing "development config"
    (let [config (assoc service-worker-base-config ::core/environment :development)]
      (is (= (core/prep config)
           (merge config
                  {:duct.server/figwheel
                   {:css-dirs ["resources" "dev/resources"]
                    :builds
                    [{:id "sw-dev"
                      :figwheel false
                      :source-paths ["dev/src" "src"]
                      :build-options
                      {:main       'foo.sw
                       :output-to   (absolute-path "target/resources/foo/public/sw.js")
                       :output-dir (absolute-path "target/resources/foo/public/sw")
                       :asset-path "/sw"
                       :closure-defines {'goog.DEBUG true}
                       :verbose    false
                       :infer-externs true
                       :language-in :es6
                       :rewrite-polyfills true
                       :preloads   '[devtools.preload]
                       :optimizations :none
                       :target :webworker}}
                     {:id           "dev"
                      :figwheel     true
                      :source-paths ["dev/src" "src"]
                      :build-options
                      {:main       'foo.client
                       :output-to  (absolute-path "target/resources/foo/public/js/main.js")
                       :output-dir (absolute-path "target/resources/foo/public/js")
                       :asset-path "/js"
                       :closure-defines {'goog.DEBUG true}
                       :verbose    false
                       :preloads   ['devtools.preload]
                       :optimizations :none}}]}}))))))

(deftest module-test
  (testing "production config"
    (is (= (core/prep base-config)
           (merge base-config
                  {:duct.compiler/cljs
                   {:builds
                    [nil
                     {:source-paths ["src"]
                      :build-options
                                    {:main       'foo.client
                                     :output-to  (absolute-path "target/resources/foo/public/js/main.js")
                                     :output-dir (absolute-path "target/resources/foo/public/js")
                                     :asset-path "/js"
                                     :closure-defines {'goog.DEBUG false}
                                     :verbose    true
                                     :optimizations :advanced}}]}}))))

  (testing "development config"
    (let [config (assoc base-config ::core/environment :development)]
      (is (= (core/prep config)
             (merge config
                    {:duct.server/figwheel
                     {:css-dirs ["resources" "dev/resources"]
                      :builds
                                [nil
                                 {:id           "dev"
                                  :figwheel     true
                                  :source-paths ["dev/src" "src"]
                                  :build-options
                                                {:main       'foo.client
                                                 :output-to  (absolute-path "target/resources/foo/public/js/main.js")
                                                 :output-dir (absolute-path "target/resources/foo/public/js")
                                                 :asset-path "/js"
                                                 :closure-defines {'goog.DEBUG true}
                                                 :verbose    false
                                                 :preloads   ['devtools.preload]
                                                 :optimizations :none}}]}}))))))