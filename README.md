# Project Title

File Deduplicator application

## Description

File Deduplicator is a simple java console (non-spring) application which, given a path on a storage,
finds and removes duplicate files.\
In order for a file to be a candidate for removal it has to satisfy a special equality operation\
Two files are considered being equal if they both have exactly the same all three of the following properties:
* size
* extension
* content

When comparing the content, the comparison is done not on the content itself but on a Base64 encoded SHA256 hash representation of it\
Content comparison is done only when two files are equal by size and extension.

## Getting Started

### Dependencies

This program requires having the following installed on your local machine
* Java version 1.8 or higher (JDK or OpenJDK)
* Apache Maven 3.X.X

### Installing

* No installation is needed only a simple clone of this repo or downloading it as an archive is enough

### Executing program

* This program uses the maven packaging and build system and once the repo is cloned or downloaded as an archive and extracted locally, the program can be run using the following commands:
```
mvn clean compile assembly:single
java -jar target/file-deduplicator-1.0-SNAPSHOT-jar-with-dependencies.jar
```

## Help

This console program has an options menu which guides the user with all of the options available.

## Authors


Vlatko Dimov


## Version History

* 1.0
    * Initial Release
