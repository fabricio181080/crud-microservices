# CRUD-Microservices UI

## Dependencies
* [Node.js](https://nodejs.org/en/)
* [Bower](http://bower.io/): `npm install -g bower`
* [Gulp](http://gulpjs.com/): `npm install -g gulp`
* [Maven](https://maven.apache.org/) (packing as _.war_)

## Before Run
In order to load all project dependencies, we must run:
```
npm install
bower install
```

## Running
* Using real backend calls: `gulp serve`
* Using stubs responses: `gulp serve --mock`
* Unit tests: `gulp test`
* Dist build: `mvn install`
