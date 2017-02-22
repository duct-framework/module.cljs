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

(defmethod ig/init-key ::build [_ options] options)

(defmethod ig/init-key :duct.module/cljs [_ _]
  (fn [config]
    (-> config
        (assoc ::build build)
        (assoc-in-default [:duct.server/figwheel :css-dirs] ["dev/resources"])
        (assoc-in-default [:duct.server/figwheel :builds]   [(ig/ref ::build)])
        (assoc-in-default [:duct.compiler/cljs :builds]     [(ig/ref ::build)]))))
