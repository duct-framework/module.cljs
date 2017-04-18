(ns duct.module.cljs-test
  (:require [clojure.java.io :as io]
            [clojure.test :refer :all]
            [duct.core :as core]
            [duct.module.cljs :as cljs]
            [integrant.core :as ig]))

(def base-config
  {:duct.core/project-ns 'foo
   :duct.core/modules    [(ig/ref :duct.module/cljs)]
   :duct.module/cljs     {:main 'foo.client}})

(defn- absolute-path [relative-path]
  (.getAbsolutePath (io/file relative-path)))

(deftest module-test
  (testing "production config"
    (is (= (core/prep base-config)
           (merge base-config
                  {:duct.compiler/cljs
                   {:builds
                    [{:source-paths ["src"]
                      :build-options
                      {:main       'foo.client
                       :output-to  (absolute-path "target/resources/foo/public/js/main.js")
                       :output-dir (absolute-path "target/resources/foo/public/js")
                       :asset-path "/js"
                       :verbose    true
                       :optimizations :advanced}}]}}))))

  (testing "development config"
    (let [config (assoc base-config ::core/environment :development)]
      (is (= (core/prep config)
           (merge config
                  {:duct.server/figwheel
                   {:css-dirs ["resources" "dev/resources"]
                    :builds
                    [{:id           "dev"
                      :figwheel     true
                      :source-paths ["dev/src" "src"]
                      :build-options
                      {:main       'foo.client
                       :output-to  (absolute-path "target/resources/foo/public/js/main.js")
                       :output-dir (absolute-path "target/resources/foo/public/js")
                       :asset-path "/js"
                       :verbose    true
                       :preloads   ['devtools.preload]
                       :optimizations :none}}]}}))))))
