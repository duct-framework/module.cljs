# Duct ClojureScript Module [![Build Status](https://github.com/duct-framework/module.cljs/actions/workflows/test.yml/badge.svg)](https://github.com/duct-framework/module.cljs/actions/workflows/test.yml)

A [Duct][] module that adds support for compiling ClojureScript for
production and development.

[duct]: https://github.com/duct-framework/duct

## Installation

Add the following dependency to your deps.edn file:

    org.duct-framework/module.cljs {:mvn/version "0.4.1"}

Or to your Leiningen project file:

    [org.duct-framework/module.cljs "0.4.1"]

## Usage

To add this module to your configuration, add the `:duct.module/cljs`
key to your configuration. You'll need to specify your main
ClojureScript namespace via the `:main` key:

```edn
{:duct.module/cljs {:main foo.client}}
```

This sets up the [compiler.cljs][] key for compiling via `lein run
:duct/compiler`, and the [server.figwheel][] key for dynamically
reloading ClojureScript files during development when calling
`(reset)` in the REPL.

[compiler.cljs]:   https://github.com/duct-framework/compiler.cljs
[server.figwheel]: https://github.com/duct-framework/server.figwheel

## License

Copyright © 2024 James Reeves

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
