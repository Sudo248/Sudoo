FROM openjdk:11-jre-slim
WORKDIR /app

COPY target/registry-0.0.1.jar registry.jar

EXPOSE 8761

ENTRYPOINT ["java","-jar", "/app/registry.jar"]