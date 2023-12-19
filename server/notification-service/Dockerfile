FROM sudo248dev/sudoo

WORKDIR /app

ENV JAR_FILE=notification-service-0.0.1.jar
ENV APP=/app/$JAR_FILE
ENV PORT=8087

COPY target/$JAR_FILE $APP
EXPOSE $PORT

CMD ["sh", "-c", "java -jar ${APP}"]