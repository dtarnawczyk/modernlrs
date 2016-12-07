# modernlrs
**Learning Record Store**

> - Implementation of well known **TinCan API** (Experience API or xAPI) - https://github.com/adlnet/xAPI-Spec

> - Main container placed within **Spring** build with **Spring Boot**.

> - All the REST requests goes through the **Jersey RESTful Web Service** (https://jersey.java.net) with the path set to "/v1"
> - Rest of the requests - MVC Spring - goes through standard root path "/"

> - For serialization/deserialization JSONs there is **Gson** library used (https://github.com/google/gson) which seems to be one of the fastest and also easy to use library.

> - To fulfill definition of modernity there is **Kotlin** instead of **Java** used as a programming language.

Properties:
-------------

  Server configuration
server.port= 8090
security.user.name= admin
security.user.password= admin321
management.security.roles= SUPERUSER
spring.jmx.enabled= false

  Spring MVC dispatcher servlet path. Needs to be different than Jersey's to enable/disable Actuator endpoints access (/info, /health, ...)
server.servlet-path= /
  Jersey dispatcher servlet
spring.jersey.application-path= /v1
spring.jersey.type= filter

  Actuator endpoints
endpoints.enabled= true
endpoints.health.enabled= true
endpoints.health.sensitive= true
endpoints.info.enabled= true
endpoints.metrics.enabled= true

  Authentication. Possible values: basic, oauth
auth= basic
auth.basic.username= user
auth.basic.password= user321
auth.oauth.key=
auth.oauth.secret=

  Cassandra DB configuration


Execution:
-------------

mvn clean spring-boot:run

mvn clean test