# ChatOp

Welcome to the ChatOp project ! It's a web application that displays rental ads for the holidays. 
The homeowner can create an ad with their informations and the users can send message to the homeowners for more informations.

## Prérequis

- Java 11 or 17
- Spring Boot 3
- MySQL
- Node.js 

## Installation

- Clone the repository
git clone https://github.com/LefClem/chatop.git

- Installation of the database

You need to install MySQL on your machine or a DBMS like MysqlWorkbench, launch it and connect with your credentials.

When Mysql is launch, create a new Database with this command: CREATE DATABASE chatop;

Update the file application.properties in the folder Chatop-server/src/main/resources with the connexion information of the database:
- spring.datasource.username
- spring.datasource.password

- Creation of the tables
When you will launch the project, Spring will automatically execute the sql script and create the tables.

- Launch the backend

Go to the folder Chatop Server and use the command mvn spring-boot:run or the start command of your IDE
 

## Frontend

- Install dependencies:
Run the command npm install

- Start the application:
Run the command npm run start or ng serve

## Documentation

You can access the swagger documentation of the API via this url : http://localhost:3001/swagger-ui/index.html
