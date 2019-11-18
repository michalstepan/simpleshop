## Simpleshop

#### Description

This simple application describes the possibilities of Spring Boot
to create a simple, yet powerful REST API.

#### Build and run
Application is to be built by maven (either embedded or provided).

`mvn clean package`

Application could be run:

`java -jar simpleshop-0.0.1-SNAPSHOT.jar`

Application uses embedded H2 database for persisting state
of web application. Consider specifying a file for persistent 
storage outside lifecycle of the app by defining parameter 
`spring.datasource.url=jdbc:h2:file:/absolute/file/to/db/file`. 

#### Documentation

There is a set-up configuration for generation of code snippets from tests.
Snippets are located in `/target/generated-snippets` folder.
One can extend current documentation by changing the
`src.main.asciidoc.api-guide.adoc` file.

There is also human-readable REST API documentation in place 
under root url `http://localhost:8080/swagger-ui.html`, or
API based documentation: `http://localhost:8080/v2/api-docs`


#### Authentication

Althrough API is not authenticated, I recommend using 
one of the standards to use authentication. JWT tokens in OAuth2
specification (short-term access tokens, long-term refresh tokens) with black/white lists would
fit the usage of stateless API and will perform a stateless
authentication.


#### Scaling

This service is defined to run on single machine within single port.
To allow this service to scale, it is important to either:
- make it a part of cluster solution with load-balancer (kubernetes, dockerized image)
- scale it locally via multiple `java -jar app.jar` commands

In either ways, it is important to set proper `server.port` for each instance to avoid conflicts.
Application doesn't store any state into the JVM, so scaling is not an issue. JWT 
authentication provides also stateless authentication.



