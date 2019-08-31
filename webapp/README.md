Sample application to demonstrate OAuth2.0 implicit grant type    
  
# Running the tests  
## Unit tests
```  
mvn clean test  
```  
  
## Run the application
### Prerequisites
* [Keycloak](https://www.keycloak.org/) running on port 8080 
* This application running on port 8082

### Test authentication flow
* Open browser and access http://localhost:8082/users
* You will be redirected to Keycloak login page
* After authenticating with valid credentials, you will be redirected back to the application and users will be displayed