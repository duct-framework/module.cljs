(defproject duct/module.cljs "0.1.2"
  :description "Duct module for developing and compiling ClojureScript"
  :url "https://github.com/duct-framework/module.cljs"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.521"]
                 [binaryage/devtools "0.9.2"]
                 [duct/core "0.2.0"]
                 [duct/compiler.cljs "0.1.0"]
                 [duct/server.figwheel "0.1.1"]
                 [integrant "0.4.0"]])
