FROM sudo248dev/sudoo

WORKDIR /app

ENV JAR_FILE=product-service-0.0.1.jar
ENV APP=/app/$JAR_FILE
ENV PORT=8083

COPY target/$JAR_FILE $APP
EXPOSE $PORT

CMD /app/start-app.sh