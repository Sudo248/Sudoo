FROM sudo248dev/sudoo

WORKDIR /app

ENV JAR_FILE=auth-service-0.0.1.jar
ENV APP=/app/$JAR_FILE
ENV PORT=8082
ENV ENABLE_OTP=false

COPY target/$JAR_FILE $APP
EXPOSE $PORT

CMD /app/start-app.sh