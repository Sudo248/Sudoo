FROM openjdk:11-jre-slim

WORKDIR /app

COPY target/image-service-0.0.1.jar image-service.jar

EXPOSE 8085

ENTRYPOINT ["java","-jar", "/app/image-service.jar"]
