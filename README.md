# Microservices with Spring Cloud demo project
The idea of this project is to practice the concepts of microservice architecture, based on Spring implementation.
As I'm focusing on the concepts, the examples are kinda simple. Also, I will keep updating the project as I learn new things about this subject.

## Architecture
Our sample microservices-based system consists of the following modules:

* <strong>gateway-service</strong> - uses Spring Cloud Gateway to acts as a proxy/gateway in our architecture.
* <strong>config-service</strong> - uses Spring Cloud Config Server to run configuration server in the native mode. The configuration files are placed on the classpath.
* <strong>discovery-service</strong> - uses Spring Cloud Netflix Eureka as an embedded discovery server.
* <strong>auth-service</strong> - receives a request to login from user and generates a Token Jwt.
* <strong>common-classes</strong> - classes that may be shared with the microservices.
* <strong>employee-service</strong> - a module containing the first of our sample microservices that allows to perform CRUD operation on in-memory repository of employees
* <strong>department-service</strong> - a module containing the second of our sample microservices that allows to perform CRUD operation on in-memory repository of departments. It communicates with employee-service.
* <strong>organization-service</strong> - a module containing the third of our sample microservices that allows to perform CRUD operation on in-memory repository of organizations. It communicates with both employee-service and organization-service.
