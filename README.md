# lottery
Final project for HelloIT/Pearl Java bootcamp

Introduction:

Lottery application. Works as REST API service with JSON as request/response, with basic GUI.
Swagger UI added for convenience (http://localhost:8080/swagger-ui.html#/).

Technologies used: Java (Spring Boot), Maven, PostgreSQL as database, (js, css, html, thymeleaf) for GUI.

DB backup file location: /resources/db/db_anton.sql

App structure

App configuration is defined in application.properties file Database properties can be found there too.

App consists of models:

Lottery - id, limit, status, titile, startDate, endDate, winnerId

Participant - id, email, age, uniqueCode

Each entity has it's own service for JPA operations and controller.


Entrypoints:

Base url - http://localhost:8080


Security:

login: admin

password: adminpass


Lottery:

/start-registration -> POST start new lottery (only admin)

/stop-registration -> POST stop lottery (only admin)

/choose-winner -> POST choose winner for the lottery (only admin)

/stats -> GET statistics on all lotteries (only admin)


Participant:

/register -> POST register new participant

/status -> GET check participant status in the lottery

______________________________________________________
/delete-lottery -> added for test purposes

/get-lottery -> added for test purposes

/delete-participant -> added for test purposes

/get-participant -> added for test purposes


Test classes:

LoginControllerTest
LotteryApplicationTests

Overall tests coverage >70%


Running app:

Start app: mvn spring-boot:run

Go to http://localhost:8080




