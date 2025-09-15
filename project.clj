(defproject org.duct-framework/module.cljs "0.5.1"
  :description "Duct module for developing and compiling ClojureScript"
  :url "https://github.com/duct-framework/module.cljs"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.12.2"]
                 [org.clojure/clojurescript "1.12.42"]
                 [binaryage/devtools "1.0.7"]
                 [org.duct-framework/compiler.cljs.shadow "0.1.4"]
                 [integrant "1.0.0"]])
