(ns duct.module.cljs
  (:require [integrant.core :as ig]))

(defn- desugar-module [mod]
  (cond
    (qualified-symbol? mod) {:init-fn mod}
    (symbol? mod)           {:entries [mod]}
    (vector? mod)           {:entries mod}
    :else                   mod))

(defmethod ig/expand-key :duct.module/cljs
  [_ {:keys [asset-path builds output-dir]
      :or   {asset-path "/cljs", output-dir "target/cljs"}}]
  {:duct.handler/file
   {:paths {asset-path {:root output-dir}}}
   (ig/profile :main :duct.compiler.cljs.shadow/release
               :test :duct.compiler.cljs.shadow/release
               :repl :duct.compiler.cljs.shadow/server)
   {:build {:target :browser
            :output-dir output-dir
            :asset-path asset-path
            :modules (update-vals builds desugar-module)}}})
