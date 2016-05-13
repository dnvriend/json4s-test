# json4s-test
A small study project on [json4s](http://json4s.org/). 

## Tagline
> One AST to rule them all! - [json4s](http://json4s.org/) 

## Wait what? Another Scala JSON library?
Well, yes, it is true that there are at least 6 json libraries for scala, not counting the java json libraries. 
All these libraries have a very similar [abstract syntax trees](https://en.wikipedia.org/wiki/Abstract_syntax_tree) (AST). 
The [json4s](http://json4s.org/) project aims to provide a single AST to be used by other scala json libraries.

## The lift web framework
There exists a web framework called [lift](http://liftweb.net/index), which is, according to the website at least_the most powerful, most secure web framework available today_ and is available on [github](https://github.com/lift). 

## lift-json
The lift framework contains a _parsing and formatting utility for JSON_ called [lift-json](https://github.com/lift/lift/tree/master/framework/lift-base/lift-json). 
A central concept in lift-json library is JSON AST which models the structure of a JSON document as a syntax tree. The lift-json utility's features are implemented in terms of the manipulation of the generated JSON AST. 
Functions are used to transform the AST itself, or to transform the AST between different formats.

## json4s-native
The _json4s_ project attempts to set _lift-json_ free from the release schedule imposed by the lift framework. 
The Lift framework carries many dependencies and as such it's typically a blocker for many other scala projects 
when a new version of scala is released. The _json4s-native_ package is in fact _lift-json_ but outside of the lift project.

So the _json4s-native_ package is in fact verbatim _lift-json_ in a different package name, this means that your import statements 
will change if you use this library.

To use the _json4s-native_ add a dependency to:

```scala
"org.json4s" %% "json4s-native" % "3.3.0"
```

You'll just have to import:

```scala
import org.json4s._
import org.json4s.native.JsonMethods._
```

