FROM openjdk:11-jre-slim
WORKDIR /app
COPY ./scripts/wait-db.sh /app/
COPY ./scripts/start-app.sh /app/
RUN chmod +x /app/wait-db.sh
RUN chmod +x /app/start-app.sh