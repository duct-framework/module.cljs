(defproject zaphodious/module.cljs "0.4.0-SNAPSHOT"
  :description "Duct module for developing and compiling ClojureScript"
  :url "https://github.com/duct-framework/module.cljs"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/clojurescript "1.10.238"]
                 [binaryage/devtools "0.9.7"]
                 [duct/core "0.6.1"]
                 [hireling "0.6.0-ALPHA-SNAPSHOT"]
                 [duct/compiler.cljs "0.2.0"]
                 [duct/server.figwheel "0.2.1"]
                 [integrant "0.6.1"]])
