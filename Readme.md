
#A MICROSERVICE BOOKSTORE CRUD APPLICATION --

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

# *  Create Modules in Spring Initializr: *
( Parent Service First )
  - Discovery-Service (Service Registry)
  - ApiGateway (Cloud Gateway)
  - Book-Service


# *  Common Dependencies: *

- Parent Starter: We must have the parent starters to indicate it as the parent, and the modules to keep the sync the child service with its parent
- Spring Cloud: Spring Cloud provides tools for developers to quickly build some of the common patterns in distributed systems like the configuration management and service discovery
- Netflix Eureka: With Netflix Eureka as service registry,  Spring Boot services can automatically register and find each other with minimal configuration
- Spring Cloud Gateway: For the API Gateway, Spring Boot can leverage Spring Cloud Gateway, which offers a more modern alternative to Netflix Zuul. It supports asynchronous and non-blocking APIs, which aligns well with the reactive programming model that Spring Boot promotes.
- Spring Web: It is a module of the Spring Framework that provides support for building web applications and RESTful APIs.
- Spring Data JPA: For Book Service, It is a part of the Spring Data project that simplifies the development of data access layers in Java applications using the Java Persistence API (JPA).
- PostgreSQL Drive: PostgreSQL JDBC Driver allows Java programs to connect to a PostgreSQL database using standard, database independent Java code.

#OTHER(not inside the Spring Initializr dependencies)
- ModelMapper: Java library that helps make mapping easy. This helps especially dealing with our dtos


# *  Service Registry Configuration: *
- In our discovery service properties file, we have the following:

- Application Name: Discovery-Service
- Server.Port: 8671  (Although it is default but It is still necessary to add it into config)
- Eureka config: Then we set to false the eureka.client.register-with-eureka and the eureka.client.fetch-registry to avoid the service registry server also identifying as client service

- It is basically use to enable discovery service and check metrics of each services like we have here:

![Screenshot (283)](https://github.com/user-attachments/assets/efa877af-16a3-43e6-aeff-75844c9948f6)


# *  Api Gateway Configuration: *
- In our api-Gateway service, we have the following:

- Application Name: API-GATEWAY
- Server.Port: 8080 (DEFAULT)
- Spring Cloud Gateway: Spring Cloud Gateway aims to provide a simple, yet effective way to route to APIs and provide cross cutting concerns to them such as: security, monitoring/metrics, and resiliency.
- Spring Eureka Client: To easily get handpicked by the eureka server and to get to know the meta-data about it like health, status, port and so on.

-Security: Security type is Basic Authentication. Implementing basic authentication in Spring Cloud Gateway enables yOY to centralize authentication logic and enforce security policies across multiple services.

# *  SECURITY CONFIGURATION: *

The security follows the reactive model since spring cloud gateway is built on reactive programming model. 

You will also need to fill the username and password in the Basic auth to compare against the hardcoded values, Which are:

 - username : korede
 - password : korede

![Screenshot (276)](https://github.com/user-attachments/assets/a6dd9e2e-3b91-49e9-bcdc-4de160f65c74)

# * NOTE 

In this configuration, we have somethings different from our regular mvc model. Like:

- SecurityWebFilterChain instead of the regular SecurityFilterChain
- ReactiveAuthenticationManager instead of the regular MVC AuthenticationManager

                
DEVELOPMENT

- Open the project on your IDE
- Startup the registry server (Discovery-Service)

- Navigate to the Book-Service
- Go to application.properties: (It is a configuration file used in Spring Boot applications to specify various properties and settings for the application.) to create the PostgreSQL connection
- Create a database called “book_service” using MySQL
- Do not forget to add your own username and password
- Add the following configuration to the file
- Run the application and there should not be any problem

- Then Fire up the API-GATEWAY (Note: If you make some certain changes to the book service especially, to the mapping address, you may have to update the API-GATEWAY Route)

- To know the services that are currently up, we need to run the eureka default server port in our browser:

http://localhost:8761/eureka/



# *BOOK SERVICE MOCK TEST *

![Screenshot (274)](https://github.com/user-attachments/assets/c33a65ca-791f-4054-86ec-5649e8c8296e)

All our test passed! That's great!


# *BOOK SERVICE POSTMAN TEST *

NOTE - Book-Service port is originally 8081, but we have to pass through the API-GATEWAY 8080 port being the single entry point.
This is achievable because we have routed the book-service to our API-GATEWAY here:

![Screenshot (278)](https://github.com/user-attachments/assets/c6a4b0d3-8e70-43a1-bc53-29490113137b)

CREATE BOOK ENDPOINT (This particular endpoint is not authenticated being the first point of contact. something like normal app sign up)

![Screenshot (277)](https://github.com/user-attachments/assets/78f6cf02-c584-428c-95c4-590c12b15205)

GET BOOK BY TITLE ENDPOINT

![Screenshot (279)](https://github.com/user-attachments/assets/9e17d563-d713-4263-899c-1291da94c149)

LIST ALL BOOKS ENDPOINT

![Screenshot (280)](https://github.com/user-attachments/assets/9bffe1aa-7d4b-4d1e-9061-b16fa506a7b0)

UPDATE BOOK ENDPOINT

![Screenshot (281)](https://github.com/user-attachments/assets/1e68c921-11e3-4fc0-a131-fe9810159924)

DELETE BOOK ENDPOINT

![Screenshot (282)](https://github.com/user-attachments/assets/20d05948-037c-42a6-9720-309e603753eb)

OUR ENDPOINTS ARE WORKING











