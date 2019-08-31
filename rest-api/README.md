Sample application to demonstrate OAuth2.0 authorization code grant type
  
# Running the tests  
## Unit tests
```  
mvn clean test  
```  
> **Note:** OAuth resource server configuration mock will be used for unit tests.  
  
## Integration tests (Using postman)
### Prerequisites
* [Keycloak](https://www.keycloak.org/) running on port 8080 
* This application running on port 8081

Import [postman collection](./postman_collection.json) and run. You can use either [postman](https://getpostman.com) or [newman](https://github.com/postmanlabs/newman) for this.