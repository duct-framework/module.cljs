(ns duct.module.cljs
  (:require [integrant.core :as ig]
            [clojure.string :as str]
            [duct.compiler.cljs :as cljs]
            [duct.core :refer [assoc-in-default target-path]]
            [duct.server.figwheel :as figwheel]
            [meta-merge.core :refer [meta-merge]]))

(defn- project-ns [config options]
  (:project-ns options (:duct.core/project-ns config)))

(defn- name-to-path [sym]
  (-> sym name (str/replace "-" "_") (str/replace "." "/")))

(defn- project-dirs [config options]
  (name-to-path (project-ns config options)))

(defn- target-public-path [config options]
  (str target-path "/resources/" (project-dirs config options) "/public"))

(defn- compiler-build [config options]
  (let [public-path (target-public-path config options)]
    {:source-paths  ["src"]
     :build-options {:main       (:main options)
                     :output-to  (str public-path "/js/main.js")
                     :output-dir (str public-path "/js")
                     :asset-path "js"
                     :optimizations :advanced}}))

(defn- figwheel-build [config options]
  (meta-merge
   (compiler-build config options)
   {:id            ::build
    :figwheel      true
    :build-options {:optimizations :none}}))

(defn- assoc-compiler [config options]
  (-> config
      (assoc-in-default [:duct.compiler/cljs :builds] [(compiler-build config options)])))

(defn- assoc-figwheel [config options]
  (-> config
      (assoc-in-default [:duct.server/figwheel :css-dirs] ["dev/resources"])
      (assoc-in-default [:duct.server/figwheel :builds] [(figwheel-build config options)])))

(defmethod ig/init-key :duct.module/cljs [_ options]
  (fn [config]
    (let [env (:environment options (:duct.core/environment config :production))]
      (cond-> config
        (= env :production)  (assoc-compiler options)
        (= env :development) (assoc-figwheel options)))))
