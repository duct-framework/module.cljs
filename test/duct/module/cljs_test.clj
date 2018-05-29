(ns duct.module.cljs-test
  (:require [clojure.java.io :as io]
            [clojure.test :refer :all]
            [duct.core :as core]
            [duct.module.cljs :as cljs]
            [integrant.core :as ig]))

(core/load-hierarchy)

(def base-config
  {:duct.profile/base {:duct.core/project-ns 'foo}
   :duct.module/cljs  {:main 'foo.client}})

(defn- absolute-path [relative-path]
  (.getAbsolutePath (io/file relative-path)))

(deftest module-test
  (testing "production config"
    (is (= {:duct.core/project-ns 'foo
            :duct.compiler/cljs
            {:builds
             [{:source-paths ["src"]
               :build-options
               {:main            'foo.client
                :output-to       (absolute-path "target/resources/foo/public/js/main.js")
                :output-dir      (absolute-path "target/resources/foo/public/js")
                :asset-path      "/js"
                :closure-defines {'goog.DEBUG false}
                :verbose         true
                :optimizations   :advanced}}]}}
           (core/build-config base-config))))

  (testing "development config"
    (let [config (assoc-in base-config
                           [:duct.profile/base :duct.core/environment]
                           :development)]
      (is (= {:duct.core/project-ns 'foo
              :duct.core/environment :development
              :duct.server/figwheel
              {:css-dirs ["resources" "dev/resources"]
               :builds
               [{:id           "dev"
                 :figwheel     true
                 :source-paths ["dev/src" "src"]
                 :build-options
                 {:main            'foo.client
                  :output-to       (absolute-path "target/resources/foo/public/js/main.js")
                  :output-dir      (absolute-path "target/resources/foo/public/js")
                  :asset-path      "/js"
                  :closure-defines {'goog.DEBUG true}
                  :verbose         false
                  :preloads        ['devtools.preload]
                  :optimizations   :none}}]}}
             (core/build-config config))))))
