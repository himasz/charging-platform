**Prerequisites to Run**

- Run with JDK 8 (it is also compatable with 17 but it need to be modified within maven pom)
- Or Docker

# Booking Service

##Notes

### Setup
- #### With Maven
- Open the terminal
- `cd` to the project directory
- build maven project and run tests `mvn clean install` to build the project
- Then start your app which will run on port `9090` with command `mvn spring-boot:run` or just run `App.java`

- #### With Docker
- `cd` to the project directory
- build maven project and run tests `mvn clean install` to build the project, run tests  and package the .jar
- build docker image locally `docker build -t bookingservice .`
- run docker image
- now can access the API

### Swagger

- Can access swagger doc from http://localhost:9090/swagger-ui.html

