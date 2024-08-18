
#A MICROSERVICE BOOKSTORE CRUD APPLICATION --

# ** Project Flow 
- The client makes an HTTP request to a specific endpoint of the application, typically initiated through a web browser or API client.
- The request passes through the gateway route directly to the instance of the specific service triggered 
- The request is received by the Controller of this service, which is responsible for handling the request.
- The Controller extracts the necessary data from the request, such as request parameters or the request body.
- The Controller invokes the appropriate method(s) in the Service layer to perform the required business logic and data operations.
- The Service layer interacts with the Repository layer to perform data access and persistence operations, if required.
- The Service layer may also perform additional processing, such as validation, transformation, or aggregation of data.
- The Service layer returns the result of the operation to the Controller.
- The Controller prepares the HTTP response, including the response status, headers, and body.
- The response is sent back to the client, completing the request-response cycle.


GENRE SERVICE

I specify authorization rules using authorizeExchange(), allowing unrestricted access to “/api/v1/service-name/create” endpoints and enabled basic authentication for rest of the endpoints. 

Basic authentication is needed for authorization. something like this to authorize each request (username: "korede" password: "korede"

The Genre service is easily the first point of contact in the code, thereafter one can savebook using the genre name among the fields

Using FeignClient the Genre interacts directly with the Book services. Which helps us finetune our responses in both services.

For Example, here we are getting a specific book here using genre name (interservice and database communication ):

![Screenshot (288)](https://github.com/user-attachments/assets/1ee9daa0-1746-4093-a55e-4b4ba7bdf7f0)




BOOK SERVICE

The Book service is easily the second point of contact in the code, immediately after saving the genre, then we can save the book 

Using FeignClient the Book interacts extensively with the Book services. Which helps us finetune our responses in both services

For Example, here we are getting a specific author here using name of the book they have written (interservice and database communication ):

![Screenshot (289)](https://github.com/user-attachments/assets/7e0fe115-8947-41eb-ab00-c21a79b4e84b)


AUTHOR SERVICE
This is like the last details one need to feed and there is a need to add the title of the book the author has wrote. The validation is done using feign client from the book service.

The code is here:

![Screenshot (290)](https://github.com/user-attachments/assets/74cfa0d6-f30a-41ec-8adb-6e1464985f6e)


# **Requirements**

BASICS

- Integrated Development Environment (IDE): IntelliJ IDEA
- Java Development Kit (JDK)
- PostgreSQL
- Postman

STEPS

- Go to spring initializr (Spring Initializr is a web-based tool provided by the Spring team that allows developers to quickly generate the basic structure and configuration files for a new Spring-based project)
  Project: Maven(Maven is a popular build automation and dependency management tool used primarily in Java projects)
- Language: Java(springboot)
- Spring Boot version: 3.2.5
- Packaging: Jar
- Java version: 17

# *  Common Dependencies: *

- Parent Starter: We must have the parent starters to indicate it as the parent, and the modules to keep the sync the child service with its parent
- Spring Cloud: Spring Cloud provides tools for developers to quickly build some of the common patterns in distributed systems like the configuration management and service discovery
- Netflix Eureka: With Netflix Eureka as service registry,  Spring Boot services can automatically register and find each other with minimal configuration
- Spring Cloud Gateway: For the API Gateway, Spring Boot can leverage Spring Cloud Gateway, which offers a more modern alternative to Netflix Zuul. It supports asynchronous and non-blocking APIs, which aligns well with the reactive programming model that Spring Boot promotes.
- OpenFeign: OpenFeign is a declarative web service client written in Java. It simplifies the process of calling RESTful web services by handling the details of each services. It works seamlessly with Spring Cloud.
- Spring Web: It is a module of the Spring Framework that provides support for building web applications and RESTful APIs.
- Spring Data JPA: For Book Service, It is a part of the Spring Data project that simplifies the development of data access layers in Java applications using the Java Persistence API (JPA).
- PostgreSQL Drive: PostgreSQL JDBC Driver allows Java programs to connect to a PostgreSQL database using standard, database independent Java code.
- MySQL Driver: MySQL JDBC Driver allows Java programs to connect to a MySQL database.
- Actuator: Actuator helps us check the metrics of our services.

#OTHERS(not inside the Spring Initializr dependencies)
- ModelMapper: Java library that helps make mapping easy. This helps especially dealing with our dtos
- ObjectMapper: object mapper is a tool used to convert data between incompatible type systems, such as converting objects to and from database rows, JSON documents, or XML files.


# *  Service Registry Configuration: *
- In our discovery service properties file, we have the following:

- Application Name: Discovery-Service
- Server.Port: 8671  (Although it is default but It is still necessary to add it into config)
- Eureka config: Then we set to false the eureka.client.register-with-eureka and the eureka.client.fetch-registry to avoid the service registry server also identifying as client service

- It is basically use to enable discovery service and check metrics of each instances like we have here:

![Screenshot (286)](https://github.com/user-attachments/assets/37dd0ab7-91a6-4210-a73c-75bb9d63c000)


# *  Api Gateway Configuration: *

API-GATEWAY serves as our single entry point. All services were routed to API-GATEWAY which uses 8080. This helps us seperate cross-cutting concerns and gives our app uniformity. To test any enpoints in the services, you have to 
pass through the GATEWAY literally. The request-response gate in our microservice is http://localhost:8080/api/v1/name-of-service/specific endpoint.

- In our api-Gateway service, we have the following:

- Application Name: API-GATEWAY
- Server.Port: 8080 (DEFAULT)
- Spring Cloud Gateway: Spring Cloud Gateway aims to provide a simple, yet effective way to route to APIs and provide cross cutting concerns to them such as: security, monitoring/metrics, and resiliency.
- Spring Eureka Client: To easily get handpicked by the eureka server and to get to know the meta-data about it's health, status, port and so on.

-Security: Security type is Basic Authentication. Implementing basic authentication in Spring Cloud Gateway enables us to centralize authentication logic and enforce security policies across multiple services. 

# *  SECURITY CONFIGURATION: *

The security follows the reactive model since spring cloud gateway is built on reactive programming model. 

You will also need to fill the username and password in the Basic auth to compare against the hardcoded values, Which are:

 - username : korede
 - password : korede

![Screenshot (287)](https://github.com/user-attachments/assets/c9007d8b-f419-49c9-b1a7-d95a92482f2c)


# * NOTE 

In the security configuration, we have somethings different from our regular non-reactive moel. Like:

- SecurityWebFilterChain instead of the regular SecurityFilterChain
- ReactiveAuthenticationManager instead of the regular MVC AuthenticationManager

-  SecurityWebFilterChain in our context it would be extended from ‘UsernamePasswordAuthenticationFilter’, responsibility of this filter is to detect if there is an attempt for authentication , if so it is forwarded to Reactive Authentication Manager.
  
-  Authentication is an interface that implement a specific type of authentication in our case ‘UsernamePasswordAuthenticationToken’ is the implementation of Authentication interface that specifies we want to authenticate using username and password.

  # * BASIC SECURITY FLOW 

  - I allowed unrestricted access to “/api/v1/service-name/create” endpoints  accross all services because they are all first point of contacts (something like sign up in a layman word)
  - When server receive a request for authentication, in the filter chain there is an authentication filter that intercept the request.
  - it validates the provided username and password against hardcoded values
  - UsernamePasswordAuthenticationToken is created using the username and password provided by the user, this token is passed to the Reactive Authentication Manager.
  -  Reactive Authentication Manager authenticates the user
   
  
# * ZIPKIN CONFIGURATION

-Because we are running it locally, I have to download the zipkin jar file directly from https://zipkin.io/pages/quickstart
- Once you have it, then run it directly from the folder the jar file was downloaded. In my case, I have it in my downloads folder. The run this commands:
   1.	cd .\Downloads
   2.	java -jar .\zipkin-server-3.4.0-exec.jar
 
      ![image](https://github.com/user-attachments/assets/994060f1-c910-41b8-8cf8-0af71656acfc)

 
Once it is running on our local machine, then we can run the code.

To monitor how our services interacts and the distributed tracing system. And also to gather timing data needed to troubleshoot latency problems in service architectures then we can check the trace and span using the port:

http://localhost:9411/zipkin/

Then we can start tracing our service interactions in real time

![Screenshot (292)](https://github.com/user-attachments/assets/52c37c6a-5f77-403d-bf56-494f21b55e9d)

![Screenshot (293)](https://github.com/user-attachments/assets/e192add7-fa4f-4f93-bf71-414a71e52bc3)





  










