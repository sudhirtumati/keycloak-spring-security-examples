Sample application to demonstrate securing spring mvc web application with spring security and Keycloak    
  
# Running the tests  
## Unit tests
```  
mvn clean test  
```  
  
## Integration tests (Using postman)
### Prerequisites
* [Keycloak](https://www.keycloak.org/) running on port 8080 
* This application running on port 8082

Import [postman collection](./postman_collection.json) and run. You can use either [postman](https://getpostman.com) or [newman](https://github.com/postmanlabs/newman) for this.