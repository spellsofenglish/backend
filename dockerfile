#Stage 1
# initialize build and set base image for first stage
FROM maven:3.8-openjdk-18-slim as stage1
# speed up Maven JVM a bit
ENV MAVEN_OPTS="-XX:+TieredCompilation -XX:TieredStopAtLevel=1"
# set working directory
WORKDIR /app
# copy just pom.xml
COPY pom.xml .
# go-offline using the pom.xml
RUN mvn dependency:go-offline
# copy your other files
COPY ./src ./src
# compile the source code and package it in a jar file
RUN mvn clean install -Dmaven.test.skip=true
#Stage 2
# set base image for second stage
FROM maven:3.8-openjdk-18-slim
# Common env
ENV SPRING_PROFILES_ACTIVE=development

# Database settings
ENV DRIVER_DB=org.postgresql.Driver
ENV URL_DB=jdbc:postgresql://postgres:5432/spellsofenglish
ENV LOGIN_DB=dbuser
ENV PASSWORD_DB=dbpassword

# Mail
ENV HOST_MAIL=localhost
ENV PORT_MAIL=1025
ENV LOGIN_MAIL=login@example.com
ENV PASSWORD_MAIL=1111
ENV PROP_MAIL_SMTP_AUTH_MAIL=true
ENV PROP_MAIL_SMTP_STARTTLS_ENABLE_MAIL=true


# Postgresql
ENV POSTGRES_DB=spellsofenglish
ENV POSTGRES_USER=dbuser
ENV POSTGRES_PASSWORD=dbpassword

# set deployment directory
WORKDIR /app
# copy over the built artifact from the maven image
COPY --from=stage1 /app/target/ /app/
RUN ls -la /app/
# entrypoint
ENTRYPOINT ["java", "-jar", "SpellsOfEnglish-0.0.1-SNAPSHOT.jar"]
