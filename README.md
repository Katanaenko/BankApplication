## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)
* [Future plans](#future-plans)

## General info
This project is a part of a bigger service on a BankApplication theme. It consists of three tables:
* ACCOUNTS
* CLIENTS
* CLIENT_TYPES  
  
The main entity in this microservice is an Account but as addition there are Client and Client_type.  
It is possible to make some CRUD operations under the Account and Client entities using REST endpoints but  
Controller class for operations with the Client is marked as deprecated because the target mode for interactions  
with the Client is using Kafka (there is another microservice where Client is used as an additional entity).

## Technologies
Project is created with:
* Java 8
* Spring
* Hibernate
* Kafka
* Liquibase
* Docker
* JUnit5
* Maven
* Postgres

## Setup
To run the project:
* Connect the project with a Tomcat 9.0.58
* Startup the Database and the Kafka using docker-compose file(docker/docker-compose.yaml)
* Startup the Tomcat with the project

Running Database and Kafka from docker-compose required for the application. Even for integration tests.
During the startup of the application Liquibase creates the Database tables and fills it with the data.

## Future plans
* Add code documentation

