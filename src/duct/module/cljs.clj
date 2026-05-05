(ns duct.module.cljs
  (:require [clojure.java.io :as io]
            [integrant.core :as ig]))

(defmethod ig/expand-key :duct.module/cljs
  [_ {:keys [asset-path main output-file output-dir]
      :or   {asset-path "/cljs", output-dir "target/cljs"}}]
  (let [release-build
        {:duct.handler/file
         {:paths {asset-path {:root output-dir}}}
         :duct.compiler.cljs.simple/build
         {:asset-path    asset-path
          :logger        (ig/refset :duct/logger)
          :main          main
          :output-dir    output-dir
          :output-to     (str (io/file output-dir output-file))
          :optimizations :advanced}}
        dev-build
        {:duct.handler/file
         {:paths {asset-path {:root output-dir}}}
         :duct.compiler.cljs.simple/build
         {:asset-path    asset-path
          :logger        (ig/refset :duct/logger)
          :main          main
          :output-dir    output-dir
          :output-to     (str (io/file output-dir output-file))
          :optimizations :none
          :preloads      ['duct.client.repl.simple.preload]}
         :duct.compiler.cljs.simple/server
         {:logger (ig/refset :duct/logger)
          :build  (ig/ref :duct.compiler.cljs.simple/build)}}]
    (ig/profile :main release-build, :test release-build, :repl dev-build))) 
