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

(defn- compiler-config [path main]
  {:duct.compiler/cljs
   {:builds ^:displace
    [{:source-paths  ["src"]
      :build-options
      {:main       main
       :output-to  (str path "/js/main.js")
       :output-dir (str path "/js")
       :asset-path "/js"
       :closure-defines {'goog.DEBUG false}
       :verbose    true
       :optimizations :advanced}}]}})

(defn- figwheel-config [path main]
  {:duct.server/figwheel
   {:css-dirs ^:displace ["resources" "dev/resources"]
    :builds   ^:displace
    [{:id           "dev"
      :figwheel     true
      :source-paths ["dev/src" "src" ]
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
               main (:main options)]
           (case (get-environment config options)
             :production  (core/merge-configs config (compiler-config path main))
             :development (core/merge-configs config (figwheel-config path main)))))})
