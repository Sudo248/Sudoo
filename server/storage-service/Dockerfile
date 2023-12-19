FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/storage-service-0.0.1.jar /app/storage-service.jar

EXPOSE 8086

ENTRYPOINT ["java","-jar", "/app/storage-service.jar"]
