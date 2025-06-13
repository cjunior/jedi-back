FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /jedi
COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests


FROM eclipse-temurin:17-jdk-alpine
WORKDIR /jedi
COPY --from=build /jedi/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]