# Multi-stage build for Analytics Service
FROM maven:3.9-eclipse-temurin-21-alpine AS builder

WORKDIR /build

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=builder /build/target/*.jar analytics-service.jar

EXPOSE 8086

ENTRYPOINT ["java", "-jar", "analytics-service.jar"]
