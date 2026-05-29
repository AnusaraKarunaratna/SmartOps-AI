# Multi-stage build for Notification Service
FROM maven:3.9-eclipse-temurin-21-alpine AS builder

WORKDIR /build

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=builder /build/target/*.jar notification-service.jar

EXPOSE 8084

ENTRYPOINT ["java", "-jar", "notification-service.jar"]
