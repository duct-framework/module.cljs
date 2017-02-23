(ns duct.module.cljs
  (:require [integrant.core :as ig]
            [duct.compiler.cljs :as cljs]
            [duct.core :refer [assoc-in-default]]
            [duct.server.figwheel :as figwheel]))

(def ^:private build
  {:id            ::build
   :source-paths  ["src"]
   :build-options {:output-to  "target/duct/js/main.js"
                   :output-dir "target/duct/js"
                   :optimizations :whitespace}})

(defn- assoc-compiler [config]
  (-> config
      (assoc-in-default [:duct.compiler/cljs :builds] [(ig/ref ::build)])))

(defn- assoc-figwheel [config]
  (-> config
      (assoc-in-default [:duct.server/figwheel :css-dirs] ["dev/resources"])
      (assoc-in-default [:duct.server/figwheel :builds]   [(ig/ref ::build)])))

(defmethod ig/init-key ::build [_ options] options)

(defmethod ig/init-key :duct.module/cljs [_ options]
  (fn [config]
    (let [env (:environment options (:duct.core/environment config :production))]
      (-> config
          (assoc ::build build)
          (cond->
            (= env :production)  (assoc-compiler)
            (= env :development) (assoc-figwheel))))))
