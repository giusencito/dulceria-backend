FROM maven:3.9.6-eclipse-temurin-11 AS build
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:11-jdk-alpine
WORKDIR /app

COPY --from=build /app/target/*.jar complete-0.0.1-SNAPSHOT.jar

EXPOSE 8083

# Comando de arranque
ENTRYPOINT ["java", "-jar", "complete-0.0.1-SNAPSHOT.jar"]