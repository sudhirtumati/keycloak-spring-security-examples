[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=com.sudhirt.practice.security%3Akeycloak-spring-security-examples&metric=alert_status)](https://sonarcloud.io/dashboard?id=com.sudhirt.practice.security%3Akeycloak-spring-security-examples)
[![Build Status](https://travis-ci.com/sudhirtumati/keycloak-spring-security-examples.svg?branch=master)](https://travis-ci.com/sudhirtumati/keycloak-spring-security-examples.svg?branch=master)
  
A set of sample application to demonstrate Keycloak integration with Spring Security for different types of applications    

# Prerequisites 
* [Keycloak](https://www.keycloak.org/)    
    
# Installing 
You may choose to run Keycloak as a standalone installation or as a docker container  
* [Server Installation and Configuration guide](https://www.keycloak.org/docs/latest/server_installation/index.html)  
* [Docker compose file](https://github.com/jboss-dockerfiles/keycloak).  
  
Perform below steps once Keycloak is up and running    
* Create a new realm. In this example realm is named as `sample-app-1` * Create 2 users   
   * User1 with `user` role  
   * User2 with `admin` role  
  
Refer to [this](https://www.baeldung.com/spring-boot-keycloak) example for Keycloak setup  
  
# Modules  
**rest-api** - demonstrates the ability to secure rest api