[![Build Status](https://travis-ci.org/materasystems/crud-microservices.svg?branch=master)](https://travis-ci.org/materasystems/crud-microservices)

## CRUD-Microservices
==========================================

This is a example project using Netflix OSS technologies to build Microservices that will run at AWS cloud. The used technologies are:

* Maven
* Java 8
* Google Guice
* Netflix OSS
  * Governator
  * Karyon
  * Hystrix
  * Eureka
  * Archaius
  * Ribbon
  * EVCache
* Jersey
* Jackson
* Cassandra
* RXJava (Observable)
* Guava
* JUnit
* Groovy / Spock
* AngularJS
* Bootstrap
* Less
* Bower
* NPM

## Running locally 

#### crudmicroservices-ui
```
$ gulp serve
```

#### crudmicroservices-edge
```
$ mvn tomcat7:run {for eureka add: -P eureka-local}
```

#### crudmicroservices-middle
```
$ mvn cassandra:start tomcat7:run {for eureka add: -P eureka-local}
```
