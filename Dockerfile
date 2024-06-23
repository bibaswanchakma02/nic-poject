#FROM --platform=linux/arm64 maven:3.8.5-openjdk-22-slim AS build
#WORKDIR /project
#
#COPY pom.xml .
#
#COPY src ./src
#
#RUN mvn package -DskipTests
#
#FROM --platform=linux/arm64 openjdk:17-jdk-slim
#WORKDIR /project
#
#COPY --from=build /project/target/project-0.0.1-SNAPSHOT.jar app.jar
#
#EXPOSE 8080
#
#ENTRYPOINT ["java", "-jar", "app.jar"]

FROM openjdk:21
ARG JAR_FILE=target/*.jar
COPY ./target/project-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]