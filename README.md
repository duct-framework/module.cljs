# Duct ClojureScript Module [![Build Status](https://github.com/duct-framework/module.cljs/actions/workflows/test.yml/badge.svg)](https://github.com/duct-framework/module.cljs/actions/workflows/test.yml)

A [Duct][] module that adds support for compiling ClojureScript for
use in web applications.

[duct]: https://github.com/duct-framework/duct

## Installation

Add the following dependency to your deps.edn file:

    org.duct-framework/module.cljs {:mvn/version "0.6.0"}

Or to your Leiningen project file:

    [org.duct-framework/module.cljs "0.6.0"]

## Usage

Add the `:duct.module/cljs`key to your configuration and set the main
namespace and the JavaScript output file:

```edn
{:duct.module/cljs {:main example.client, :output-file "client.js"}}
```

This configuration will generate a file `target/cljs/client.js` from the
`example.client` namespace. The `target/cljs` directory will be added to
the web application's static file handler, `:duct.handler/file`. This
will make the generated JavaScript accessible at `/cljs/client.js`.

The module can be configured further with the options:

- `:output-dir` - the directory to put the compiled JavaScript in
  (defaults to `"target/cljs"`)
- `:asset-path` - the web server path where the compiled JavaScript can
  be accessed (defaults to `"/cljs"`)

When run in under the REPL profile, a server will be started that will
update the compiled JavaScript each time the environment is `(reset)`.

When run in under the main profile, the ClojureScript will be compiled
for production. This will be slower, but produce a smaller output.

## License

Copyright © 2026 James Reeves

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
