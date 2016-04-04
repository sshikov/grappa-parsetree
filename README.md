# grappa-parsetree

The license of this project is Apache 2.0.

The latest version of grappa-parsetree is 1.0.1, although 1.1.0-beta.3 is also available. It requires Java 8 or later.

## What Is This?

This is an extension to the [Grappa](https://github.com/fge/grappa) library. This project has
Grappa as a dependency, and as such everything that is capable in Grappa is possible here.

This project provides a mechanism for building a [Parse Tree](https://en.wikipedia.org/wiki/Parse_tree)  from the
grammars you define.

## Using grappa-parsetree
[Example.java](https://github.com/ChrisBrenton/grappa-parsetree/blob/master/src/test/java/com/github/chrisbrenton/grappa/parsetree/example/Example.java)
has a working example of how to use this library. It does not perform any specific tasks, however
. There is also a more complex example in the testing directory.

## Adding To Your Project
Adding this library to your project is simple:

### Gradle
```groovy
dependencies {
    compile(group: "com.github.chrisbrenton", name: "grappa-parsetree", version: "1.0.0")
}
```
### Maven
```
<dependencies>
    <dependency>
        <groupId>com.github.chrisbrenton</groupId>
        <artifactId>grappa-parsetree</artifactId>
        <version>1.0.0</version>
    </dependency>
</dependencies>
```

## Getting Help
Support for this library can be found using IRC (channel: `#grappa` server: `chat.freenode.net`).

I am also contactable by email &lt;chrisbrenton90 at gmail dot com&gt;

## Acknowledgements
Thanks for this project go to:
* Francis Galiegue - Author of [Grappa](https://github.com/fge/grappa) for the support and
contributions to this extension.
