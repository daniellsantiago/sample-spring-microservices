# Microservices with Spring Cloud demo project
The idea of this project is to practice the concepts of the microservice architecture, based on Spring implementation.
As I'm a beginner student of this new entire world, I'll try to make things as much simple as I can, at least in the beginning.

## Architecture
Our sample microservices-based system consists of the following modules:

* <strong>gateway-service</strong> - a module that uses Spring Cloud Netflix Zuul to acts as a proxy/gateway in our architecture.
* <strong>config-service</strong> - a module that uses Spring Cloud Config Server to run configuration server in the native mode. The configuration files are placed on the classpath.
* <strong>discovery-service</strong> - a module that uses Spring Cloud Netflix Eureka as an embedded discovery server.
* <strong>employee-service</strong> - a module containing the first of our sample microservices that allows to perform CRUD operation on in-memory repository of employees
* <strong>department-service</strong> - a module containing the second of our sample microservices that allows to perform CRUD operation on in-memory repository of departments. It communicates with employee-service.
* <strong>organization-service</strong> - a module containing the third of our sample microservices that allows to perform CRUD operation on in-memory repository of organizations. It communicates with both employee-service and organization-service.
