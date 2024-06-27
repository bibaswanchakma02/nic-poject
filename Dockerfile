FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY ./target/project-0.0.1-SNAPSHOT.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]


#FROM openjdk:21
#ARG JAR_FILE=target/*.jar
#COPY ./target/project-0.0.1-SNAPSHOT.jar app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]