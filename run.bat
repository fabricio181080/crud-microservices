@echo off

start cmd /k "title CRUDMICROSERVICES_EDGE & mvn -pl crudmicroservices-edge tomcat7:run"
start cmd /k "title CRUDMICROSERVICES_MIDDLE & mvn -pl crudmicroservices-middle cassadra:start tomcat7:run"
start cmd /k "cd .\crudmicroservices-ui & title CRUDMICROSERVICES_UI & gulp serve"