(ns duct.module.cljs
  (:require [integrant.core :as ig]))

(defn- desugar-module [mod]
  (cond
    (qualified-symbol? mod) {:init-fn mod}
    (symbol? mod)           {:entries [mod]}
    (vector? mod)           {:entries mod}
    :else                   mod))

(defmethod ig/expand-key :duct.module/cljs
  [_ {:keys [modules]}]
  {:duct.handler/file
   {:paths {"/cljs" {:root "target/cljs"}}}
   (ig/profile :main :duct.compiler.cljs.shadow/release
               :repl :duct.compiler.cljs.shadow/server)
   {:build {:target :browser
            :output-dir "target/cljs"
            :asset-path "/cljs"
            :modules (update-vals modules desugar-module)}}})
