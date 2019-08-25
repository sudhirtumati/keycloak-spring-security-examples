
Sample application to demonstrate Keycloak integration with Spring Security  
  
# Prerequisites  
* [Keycloak](https://www.keycloak.org/)  
  
# Installing  
You may choose to run Keycloak as a standalone installation or as a docker container
* [Server Installation and Configuration guide](https://www.keycloak.org/docs/latest/server_installation/index.html)
* [Docker compose file](https://github.com/jboss-dockerfiles/keycloak).

Perform below steps once Keycloak is up and running  
* Create a new realm. In this example realm is named as `sample-app-1`  
* Create 2 users 
	* User1 with `user` role
	* User2 with `admin` role

Refer to [this](https://www.baeldung.com/spring-boot-keycloak) example for Keycloak setup

# Running the tests
## Unit tests (Using Spring MockMVC)
```
mvn clean test
```
> **Note:** OAuth resource server configuration mock will be used for unit tests.

## Integration tests (Using postman)
Import [postman collection](./postman_collection.json) and run. You can use either [postman](https://getpostman.com) or [newman](https://github.com/postmanlabs/newman) for this. 