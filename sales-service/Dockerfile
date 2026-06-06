# Multi-stage build for Sales Service
FROM maven:3.9-eclipse-temurin-21-alpine AS builder

WORKDIR /build

COPY pom.xml .
COPY .mvn ./.mvn
COPY mvnw .
COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=builder /build/target/*.jar sales-service.jar

EXPOSE 8085

ENTRYPOINT ["java", "-jar", "sales-service.jar"]
