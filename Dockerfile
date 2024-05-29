FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY build/libs/mindglow-0.0.1-SNAPSHOT.jar springboot-app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","springboot-app.jar"]