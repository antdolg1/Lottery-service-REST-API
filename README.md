# lottery
Final project for HelloIT/Pearl Java bootcamp
Introduction

Lottery application. Works as REST API service with JSON as response.
Technologies used: Java (Spring Boot), Maven, PostgreSQL as database

App structure

App configuration is defined in application.properties file Database properties can be found there too.

App consists of models:

Lottery - id, limit, status, titile, startDate, endDate, winnerId

Participant - id, email, age, uniqueCode

Each entity has it's own service for JPA operations and controller.


Entrypoints:

Base url - http://localhost:8080


Lottery:

/start-registration -> POST start new lottery

/stop-registration -> POST stop lottery

/choose-winner -> POST choose winner for the lottery

/stats -> GET statistics on all lotteries


Participant:

/register -> POST register new participant

/status -> GET check participant status in the lotery


Test classes:

Pending...


Running app:

Start app: mvn spring-boot:run

Go to http://localhost:8080


