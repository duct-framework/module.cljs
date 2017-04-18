# Duct module.cljs

[![Build Status](https://travis-ci.org/duct-framework/module.cljs.svg?branch=master)](https://travis-ci.org/duct-framework/module.cljs)

A [Duct][] module that adds support for compiling ClojureScript for
production and development.

[duct]: https://github.com/duct-framework/duct

## Installation

To install, add the following to your project `:dependencies`:

    [duct/module.cljs "0.1.1"]

## Usage

To add this module to your configuration, add a reference to
`:duct.module/cljs` to `:duct.core/modules`. You'll need to specify
your main ClojureScript namespace via the `:main` key:

```edn
{:duct.core/modules [#ref :duct.module/cljs]
 :duct.module/cljs  {:main foo.client}}
```

This sets up the [compiler.cljs][] key for compiling with `lein duct
compile`, and the [server.figwheel][] key for dynamically reloading
ClojureScript files when calling `(reset)` in the REPL.

[compiler.cljs]:   https://github.com/duct-framework/compiler.cljs
[server.figwheel]: https://github.com/duct-framework/server.figwheel

## License

Copyright © 2017 James Reeves

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
