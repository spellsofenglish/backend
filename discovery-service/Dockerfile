FROM gradle:latest AS BUILD
WORKDIR /usr/app/
COPY  . .
RUN gradle build

FROM khipu/openjdk17-alpine
ENV JAR_NAME=discovery-service-0.0.1-SNAPSHOT.jar
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY --from=BUILD /usr/app/build/libs/$JAR_NAME $APP_HOME
ENTRYPOINT exec java -jar $APP_HOME/$JAR_NAME


