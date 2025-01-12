(defproject org.duct-framework/module.cljs "0.4.1"
  :description "Duct module for developing and compiling ClojureScript"
  :url "https://github.com/duct-framework/module.cljs"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.12.0"]
                 [org.clojure/clojurescript "1.11.132"]
                 [binaryage/devtools "1.0.7"]
                 [org.duct-framework/compiler.cljs.shadow "0.1.2"]
                 [integrant "0.13.1"]])
