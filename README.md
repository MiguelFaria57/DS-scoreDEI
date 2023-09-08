# DS - scoreDEI

<br>

## Description
Development of a platform for consulting sports results in real time, including web system with data layer and frontend through Spring Boot and Thymeleaf, database through JPA and integration with external service using REST technology to obtain data.

### Technologies:
- Java
- Spring
- JPA

<br>

## Information
The code need to be part of a Spring Boot project wit Maven.

To run the platform it is necessary to have a functional database. To do this, using a tool like pgadmin, it is necessary to create a new database with the name scoreDEI, username: postgress and password: password.

If you run the project from the source code, you can configure the database to your liking using the application.properties file. You must generate the tables using the SQL file that is present together with this document.

Running the project and accessing localhost, you will have to enter the platform with the admin entered by default, which has the username: admin and password: password as credentials. You can access /menu and, by clicking on the link to populate the DB, it becomes easier to test existing functionalities.

Note that since API requests are limited to 10 per minute, for the total population it is necessary to access Populate DB multiple times.

With this document, the .war file is present as mentioned in the statement. However, it was not possible to find a guide that would allow us to test it. Therefore, the .jar file is also present.

### Note
This project uses an external API: "API-Sports.io"

<br>

## Authors: 
- Miguel Faria
- João Monteiro
- António Correia