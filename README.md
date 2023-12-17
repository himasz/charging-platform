**Prerequisites to Run**
- Run with JDK 17
- Run Docker

# charging-solutions-platform

##Notes

### Setup
- #### Before running by maven
- Install postgres 
- open terminal then type `psql` to a postgres shell
- create a user and its password (command: `CREATE ROLE postgres WITH LOGIN PASSWORD '123321';`)
- create a database with the user (I used a db named `charges` like that `CREATE DATABASE charge;`)

- #### With Maven
- Open the terminal
- `cd` to the project directory
- build maven project and run tests `mvn clean install` to build the project
- Then start your app which will run on port `9090` with command `mvn spring-boot:run` or just run `Application.java`
- now you can access the API, I have added a postman collection into resources

- #### With Docker
- `cd` to the project directory
- build maven project and run tests `mvn clean install` to build the project, run tests  and package the .jar
- build docker image locally `docker build -t charging-solutions-platform .`
- run docker compose `docker-compose up -d`
- now you can access the API, I have added a postman collection into resources

### Swagger
- Can access swagger doc from http://localhost:9090/swagger-ui.html

