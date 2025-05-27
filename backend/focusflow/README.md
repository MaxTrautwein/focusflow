# Backend FocusFlow

You may run this app by either installing it locally or by using Docker

## Docker
### 1. Prerequisites

Install Docker: https://docs.docker.com/get-started/get-docker/

### 2. Update application.properties
The the [application.properties](src\main\resources\application.properties) Update `localhost` to `focusflow-db`.

### 3. Run the application

Go to the Root of the Repository and open a Console
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


### 3. Unit tests

To run the units tests which can be found in the folder:

    src\test\java\hse\group1\focusflow

You have to do the following in the terminal:

    cd backend/focusflow
    mvn test


## API Endpoints

### [**Postman Workspace**](https://www.postman.com/eggheads/workspace/sw-testing/collection/34074908-37baa637-942f-4ad4-a9c3-a373d98ad6e6?action=share&creator=34074908&active-environment=34074908-516c9aaf-35df-4fdc-8f9c-5b48c3a5c33c)

**Base URL**:
```
http://localhost:8080
```
### User Endpoints

#### Register a new user

POST /api/users/register

Request:

    {
    "email":     "alice@example.com",
    "password":  "verysecretpw",
    "firstName": "Alice",
    "lastName":  "Smith"
    }

Response (200 OK):

    {
    "id":       1,
    "email":    "alice@example.com",
    "firstName":"Alice",
    "lastName": "Smith",
    "teamId":   null
    }

#### Log in

POST /api/users/login

Request:
```
{
  "email":    "alice@example.com",
  "password": "verysecretpw"
}
```

Response (200 OK)
```
{
  "id":       1,
  "email":    "alice@example.com",
  "firstName":"Alice",
  "lastName": "Smith",
  "teamId":   null
}
```

#### Get current user

GET /api/users/me

Response (200 OK):
```
{
  "id":       1,
  "email":    "alice@example.com",
  "firstName":"Alice",
  "lastName": "Smith",
  "teamId":   null
}
```

#### Task Endpoints
#### List all tasks

GET /api/tasks

Response: (200 OK):
```
[
    {
        "title": "Write report",
        "short_description": "Monthly finance summary",
        "long_description": "Compile data from all departments",
        "due_date": "2025-06-30T15:00:00Z",
        "priority": "HIGH",
        "status": "PENDING",
        "assignee": {
            "id": 1,
            "email": "test@example.com",
            "firstName": "Max",
            "lastName": "Mustermann",
            "teamId": null
        },
        "task_id": 2
    },
    {
        "title": "Write final report",
        "short_description": "Include Q2 data",
        "long_description": "Add graphs and summary",
        "due_date": "2025-07-05T12:00:00Z",
        "priority": "MEDIUM",
        "status": "PENDING",
        "assignee": {
            "id": 1,
            "email": "test@example.com",
            "firstName": "Max",
            "lastName": "Mustermann",
            "teamId": null
        },
        "task_id": 1
    }
]
```

#### Get one task
GET /api/tasks/{taskId}

Example:
```
GET /api/tasks/1
```

```
{
    "title": "Write final report",
    "short_description": "Include Q2 data",
    "long_description": "Add graphs and summary",
    "due_date": "2025-07-05T12:00:00Z",
    "priority": "MEDIUM",
    "status": "PENDING",
    "assignee": {
        "id": 1,
        "email": "test@example.com",
        "firstName": "Max",
        "lastName": "Mustermann",
        "teamId": null
    },
    "task_id": 1
}
```

#### Create a task
POST /api/tasks

Request:
```
{
  "title":            "Test tests",
  "short_description":"Testing the test",
  "long_description": "Testing the tests that test",
  "due_date":         "2025-06-30T15:00:00Z",
  "assignee":         { "id": 1 },
  "priority":         "HIGH",
  "status":           "PENDING"
}
```

Response (201 Created):

```
{
    "title": "Test tests",
    "short_description": "Testing the test",
    "long_description": "Testing the tests that test",
    "due_date": "2025-06-30T15:00:00Z",
    "priority": "HIGH",
    "status": "PENDING",
    "assignee": {
        "id": 1,
        "email": "test@example.com",
        "firstName": "Max",
        "lastName": "Mustermann",
        "teamId": null
    },
    "task_id": 52
}
```

#### Update a task

PUT /api/tasks/{taskId}

Example:
```
PUT /api/tasks/2
```

Request:
```
{
  "title":            "Write final report",
  "short_description":"Include Q2 data",
  "long_description": "Add graphs and summary",
  "due_date":         "2025-07-05T12:00:00Z",
  "assignee":         { "id": 1 },
  "priority":         "MEDIUM",
  "status":           "PENDING"
}
```

Response (200 OK):

```
{
    "title": "Write final report",
    "short_description": "Include Q2 data",
    "long_description": "Add graphs and summary",
    "due_date": "2025-07-05T12:00:00Z",
    "priority": "MEDIUM",
    "status": "PENDING",
    "assignee": {
        "id": 1,
        "email": "test@example.com",
        "firstName": "Max",
        "lastName": "Mustermann",
        "teamId": null
    },
    "task_id": 52
}
```

#### Delete a task

DELETE /api/tasks/{taskId}

Example:
```
DELETE /api/tasks/2
```

Response (204 No Content):
```
(empty body)
```