[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=com.sudhirt.practice.security%3Akeycloak-spring-security-examples&metric=alert_status)](https://sonarcloud.io/dashboard?id=com.sudhirt.practice.security%3Akeycloak-spring-security-examples)
[![Build Status](https://travis-ci.com/sudhirtumati/keycloak-spring-security-examples.svg?branch=master)](https://travis-ci.com/sudhirtumati/keycloak-spring-security-examples.svg?branch=master)
  
Sample application to demonstrate security rest api with spring security and Keycloak    
  
# Running the tests  
## Unit tests
```  
mvn clean test  
```  
# Running the application
## Prerequisites
* [Keycloak](https://www.keycloak.org/) running on port 8080 
* This application running on port 8082
```
http://localhost:8082/app/users
```