# Backend FocusFlow

You may run this app by either installing it loccaly or by using Docker

## Docker
### 1. Prerequisites

Install Docker: https://docs.docker.com/get-started/get-docker/

### 2. Update application.properties
The the [application.properties](src\main\resources\application.properties) Update `localhost` to `focusflow-db`.

### 3. Run the application

Go to the Root of the Repository and upen a Console
```
docker compose up
```

To rebuild use
```
docker compose up --build
```


## Local Install
### 1. Prerequisites

To run and test this project, make sure you have:

- **Java 21** (installed, JAVA_HOME/PATH variable set correctly)
- **Maven** (installed and PATH variable set)
- **PostgreSQL 16** (installed and running locally)

#### Install PostgreSQL 16

- Download the installer from the official page: https://www.postgresql.org/download/
- Choose version **16**
- During installation:
  - During the installation process you will be asked to choose a password. Look at [application.properties](src\main\resources\application.properties) for password and other information regarding the connection with the database.
  - Leave the port as **5432** (default)

##### Run only PostgreSQL in docker

```
docker compose up postgres
```

### 2. Run the application

You can verify your setup with:
```
    java -version
    mvn -v
```
You can start the Spring Boot application using:
```
    cd backend/focusflow
    mvn spring-boot:run
```
### 3. Connection

To see if the connection is working, you can reach it at:
```
    http://localhost:8080
```