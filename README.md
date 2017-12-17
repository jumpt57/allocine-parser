# Allociné Parser

Allociné Parser is a project designed to play with different elements of the Java language in version 8 as 
well as other libraries and frameworks (such as multi-threading, data mapping via Jsoup, persistence with mongodb, 
lambdas, streams etc.) in a development context of a program to retrieve data from Allociné (the French IMDB).

The program will parse Allociné movie cards to transform them into BSON object which will then be 
saved in a NoSQL database (MongoDb as of today).

Because Allociné contains hundreds of thousands of films, it is not possible to recover them one by one. The goal 
is to set up a multiple recovery via the Java multi-threading API.

## Built With

* [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) - Programming language
* [Apache Maven](https://maven.apache.org/) - Build automation tool
* [Jsoup](https://jsoup.org/) - Java library for working with real-world HTML
* [Mongodb](https://www.mongodb.com/fr) - Free and open-source cross-platform document-oriented database program
* [Morphia](https://mongodb.github.io/morphia/) - Transparently map your Java entities to MongoDB documents and back.
* [Apache Commons Lang](https://commons.apache.org/proper/commons-lang/) - The standard Java libraries fail to provide enough methods for manipulation of its core classes. Apache Commons Lang provides these extra methods.
* [IntelliJ IDEA](https://www.jetbrains.com/idea/) -  Java integrated development environment (IDE)
