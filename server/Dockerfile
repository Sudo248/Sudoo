FROM openjdk:11-jre-slim
WORKDIR /app
COPY ./wait-db.sh /app/
COPY ./start-app.sh /app/
RUN chmod +x /app/wait-db.sh
RUN chmod +x /app/start-app.sh