FROM maven:3.9.6-eclipse-temurin-21 AS builder

FROM eclipse-temurin:21.0.9_10-jdk

WORKDIR /app

COPY target/tech-challenge-0.0.1.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]