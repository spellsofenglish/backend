FROM openjdk:17-oracle

WORKDIR /usr/app/

COPY ./build/libs/auth-service-0.0.1-SNAPSHOT.jar /usr/app/

ENTRYPOINT ["java", "-jar", "-Xms512m", "-Xmx1024m", "auth-service-0.0.1-SNAPSHOT.jar"]
