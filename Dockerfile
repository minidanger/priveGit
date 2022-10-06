FROM openjdk:17-jdk-alpine
VOLUME /tmp
EXPOSE 8080
ADD ordering-0.0.1-SNAPSHOT.jar app-1.1.jar
ENTRYPOINT ["java","-jar","app-1.1.jar"]