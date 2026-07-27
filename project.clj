(defproject org.duct-framework/module.cljs "0.6.0"
  :description "Duct module for developing and compiling ClojureScript"
  :url "https://github.com/duct-framework/module.cljs"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.12.5"]
                 [org.clojure/clojurescript "1.12.145"]
                 [binaryage/devtools "1.0.7"]
                 [org.duct-framework/compiler.cljs.simple "0.1.1"]
                 [integrant "1.0.1"]])
