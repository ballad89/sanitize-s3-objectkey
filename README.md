# sanitize-s3-objectkey

This project is pretty much a translation of [sanitize-s3-objectkey](https://github.com/Advanon/sanitize-s3-objectkey) into clojure with some minor changes.

## Installation

Add the following dependency to your `project.clj` file

[![Clojars Project](http://clojars.org/sanitize-s3-objectkey/latest-version.svg)](http://clojars.org/sanitize-s3-objectkey)

## Usage

```clojure
(require '[sanitize-s3-objectkey.core :refer [sanitize]])

(sanitize "áêīòü") ; aeiou
(sanitize "pipes|are|not|valid") ; pipesarenotvalid
(sanitize "spaces should be replaced") ; spaces-should-be-replaced
(sanitize "spaces should be replaced" "/") ; spaces/should/be/replaced
```
