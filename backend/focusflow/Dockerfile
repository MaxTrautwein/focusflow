FROM maven:3.9.9-eclipse-temurin-21-alpine AS builder

WORKDIR /app

COPY pom.xml ./
COPY .mvn .mvn
COPY mvnw ./
COPY mvnw.cmd ./

# Download dependencies
RUN mvn dependency:go-offline -B

# Copy the rest of the application code
COPY src ./src

# Package the application
RUN mvn package


FROM openjdk:25-slim-bullseye

WORKDIR /app

COPY --from=builder /app/target/*.jar  /app/app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]

