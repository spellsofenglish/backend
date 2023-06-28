FROM gradle:latest AS BUILD
WORKDIR /usr/app/
COPY  . .
RUN gradle build

# Package stage
FROM khipu/openjdk17-alpine
ENV JAR_NAME=gateway-service-0.0.1-SNAPSHOT.jar
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY --from=BUILD $APP_HOME .
ENTRYPOINT exec java -jar $APP_HOME/build/libs/$JAR_NAME