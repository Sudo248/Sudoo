FROM openjdk:11-jre-slim

WORKDIR /app

COPY target/storage-service-0.0.1.jar storage-service.jar

EXPOSE 8086

ENTRYPOINT ["java","-jar", "/app/storage-service.jar"]
