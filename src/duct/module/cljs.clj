(ns duct.module.cljs
  (:require [clojure.string :as str]
            [duct.core :as core]
            [integrant.core :as ig]))

(defn- get-environment [config options]
  (:environment options (:duct.core/environment config :production)))

(defn- project-ns [config options]
  (:project-ns options (:duct.core/project-ns config)))

(defn- name-to-path [sym]
  (-> sym name (str/replace "-" "_") (str/replace "." "/")))

(defn- project-dirs [config options]
  (name-to-path (project-ns config options)))

(defn- target-public-path [config options]
  (str core/target-path "/resources/" (project-dirs config options) "/public"))

(defn- compiler-config [path main sw-main]
  {:duct.compiler/cljs
   {:builds ^:displace
            [(when sw-main
               {:id           "sw"
                :source-paths ["src"]
                :build-options
                              {:main              sw-main
                               :output-to         (str path "/sw.js")
                               :output-dir        (str path "/sw")
                               :asset-path        "/sw"
                               :closure-defines   {'goog.DEBUG false}
                               :verbose           true
                               :infer-externs     true
                               :language-in       :es6
                               :rewrite-polyfills true
                               :optimizations     :advanced
                               :target            :webworker}})
             {:source-paths ["src"]
              :build-options
                            {:main            main
                             :output-to       (str path "/js/main.js")
                             :output-dir      (str path "/js")
                             :asset-path      "/js"
                             :closure-defines {'goog.DEBUG false}
                             :verbose         true
                             :optimizations   :advanced}}]}})

(defn- figwheel-config [path main sw-main]
  {:duct.server/figwheel
   {:css-dirs ^:displace ["resources" "dev/resources"]
    :builds   ^:displace
    [(when sw-main
       {:id "sw-dev"
        :figwheel false
        :source-paths ["dev/src" "src"]
        :build-options
        {:main       sw-main
         :output-to  (str path "/sw.js")
         :output-dir (str path "/sw")
         :asset-path "/sw"
         :closure-defines {'goog.DEBUG true}
         :verbose    false
         :infer-externs true
         :language-in :es6
         :rewrite-polyfills true
         :preloads   '[devtools.preload]
         :optimizations :none
         :target :webworker}})
     {:id           "dev"
      :figwheel     true
      :source-paths ["dev/src" "src"]
      :build-options
      {:main       main
       :output-to  (str path "/js/main.js")
       :output-dir (str path "/js")
       :asset-path "/js"
       :closure-defines {'goog.DEBUG true}
       :verbose    false
       :preloads   '[devtools.preload]
       :optimizations :none}}]}})

(defmethod ig/init-key :duct.module/cljs [_ options]
  {:fn (fn [config]
         (let [path (target-public-path config options)
               sw-main (:sw-main options)
               main (:main options)]
           (case (get-environment config options)
             :production  (core/merge-configs config (compiler-config path main sw-main))
             :development (core/merge-configs config (figwheel-config path main sw-main)))))})
