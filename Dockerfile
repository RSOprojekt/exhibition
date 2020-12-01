FROM adoptopenjdk:14-jre-hotspot

WORKDIR /app

ADD ./api/target/exhibition-api-1.0.0-SNAPSHOT.jar /app

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "exhibition-api-1.0.0-SNAPSHOT.jar"]