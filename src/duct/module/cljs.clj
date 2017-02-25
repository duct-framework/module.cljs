(ns duct.module.cljs
  (:require [integrant.core :as ig]
            [duct.compiler.cljs :as cljs]
            [duct.core :refer [assoc-in-default]]
            [duct.server.figwheel :as figwheel]))

(defn- compiler-build [{:keys [main]}]
  {:source-paths  ["src"]
   :build-options {:main       main
                   :output-to  "target/assets/js/main.js"
                   :output-dir "target/assets/js"
                   :optimizations :advanced}})

(defn- figwheel-build [{:keys [main]}]
  {:id            ::build
   :figwheel      true
   :source-paths  ["src"]
   :build-options {:main       main
                   :output-to  "target/assets/js/main.js"
                   :output-dir "target/assets/js"
                   :asset-path "js"
                   :optimizations :none}})

(defn- assoc-compiler [config options]
  (-> config
      (assoc-in-default [:duct.compiler/cljs :builds] [(compiler-build options)])))

(defn- assoc-figwheel [config options]
  (-> config
      (assoc-in-default [:duct.server/figwheel :css-dirs] ["dev/resources"])
      (assoc-in-default [:duct.server/figwheel :builds]   [(figwheel-build options)])))

(defmethod ig/init-key :duct.module/cljs [_ options]
  (fn [config]
    (let [env (:environment options (:duct.core/environment config :production))]
      (cond-> config
        (= env :production)  (assoc-compiler options)
        (= env :development) (assoc-figwheel options)))))
