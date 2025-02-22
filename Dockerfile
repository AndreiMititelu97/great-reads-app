FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/devmind-fullstack-project-1.0-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]