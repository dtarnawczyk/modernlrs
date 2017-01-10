# modernlrs #
### Learning Record Store ###

> - Implementation of the **TinCan API** (Experience API or xAPI)
https://github.com/adlnet/xAPI-Spec
> - Main container placed within **Spring** build with **Spring Boot**.
> - All the REST requests goes through the **Jersey RESTful Web Service** (https://jersey.java.net) with the path set to "/v1"
> - Rest of the requests - MVC Spring - goes through standard root path "/"
> - For serialization/deserialization JSONs there is **Gson** library used (https://github.com/google/gson) which seems to be one of the fastest and also easy to use library.
> - Used database **MongoDB** for storing document objects.
> - To fulfill definition of modernity there is **Kotlin** instead of **Java**.


##### *application.properties*  - properties file: #####

```sh
###### Server configuration ######
server.port= 8090
security.user.name= admin
security.user.password= admin321
management.security.roles= SUPERUSER
spring.jmx.enabled= false
###### Spring MVC dispatcher servlet path. Path needs to be different than Jersey's to enable/disable Actuator endpoints access (/info, /health, ...) ######
server.servlet-path= /
###### Jersey dispatcher servlet ######
spring.jersey.application-path= /v1
spring.jersey.type= filter
###### Actuator endpoints ######
endpoints.enabled= true
endpoints.health.enabled= true
endpoints.health.sensitive= true
endpoints.info.enabled= true
endpoints.metrics.enabled= true
###### Authentication. Possible values: basic, oauth ######
auth= basic
auth.basic.username= user
auth.basic.password= user321
auth.oauth.key=
auth.oauth.secret=
###### Database. Options: mongodb ######
database.type = mongodb
###### MongoDB configuration ######
database.mongodb.host= 127.0.0.1
database.mongodb.port= 27017
database.mongodb.database= modernlrs
logging.level.org.springframework.data.mongodb.core.index=OFF
###### Xapi version ######
xapi.version = 1.0.3
```


##### Building and running: #####

Clone repository
```sh
$  git clone https://github.com/dtarnawczyk/modernlrs
```

Open project folder
```sh
$  cd modernlrs/
```

Run application
```sh
$  mvn clean spring-boot:run
```

Test application
```sh
$  mvn clean test
```

##### Available resources #####


###### Statements: ######


> - POST for saving Statement object into database. Statement as a JSON within a body of the request.
> - GET for listing available Statements.

```sh
localhost:8090/v1/xAPI/statements
```


> - PUT with {statementId} for saving Statement object into database with custom Id provided in URL. Statement as a JSON within a body of the request.
> - GET for retrieving Statement base on {statementId} from a database.

```sh
localhost:8090/v1/xAPI/statements/{statementId}
```

###### Activities: ######


> - POST for retrieving Activity from a database. 'activityId' within a JSON in a body of the request. In response full information of a requested Activity.

```sh
localhost:8090/v1/xAPI/activities
```

###### Agents: ######

> - POST for retrieving Agents from a database. Incomplete Actor object as a JSON within a body of the request. In response full information of a requested Actor.

```sh
localhost:8090/v1/xAPI/agents
```


#####  Kotlin specifics ######

> - Customized prefix **&{..}** instead of default **${..}** used for example in **@Value("&{..}")**
