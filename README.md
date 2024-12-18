# ChatOp

Welcome to the ChatOp project ! It's a web application that displays rental ads for the holidays. 
The homeowner can create an ad with their informations and the users can send message to the homeowners for more informations.

## Prérequis

Java 11 or 17
Spring Boot 3
MySQL
Node.js et npm (for the Angular project)

## Installation

#Clone the repository
git clone https://github.com/LefClem/chatop.git

#Creation of the database
CREATE DATABASE chatop;

Update the file application.properties with the connexion information of the database.

#Go inside folder:
cd chatop

#Creation of the tables
When you will launch the project, Spring will automatically execute the sql script and create the tables.

##Launch the back-end
Run the WebappApplication file or use the command mvn spring-boot:run    

##Launch the front-end

#Install dependencies:
npm install

#Start the application:
npm run start

## Documentation

You can access the swagger documentation of the API via this url : http://localhost:3001/swagger-ui/index.html
