FROM openjdk:12.0.2
WORKDIR /app
COPY target/dispatcher-0.0.1-SNAPSHOT.jar .
EXPOSE 8187
ENTRYPOINT ["java", "-jar", "dispatcher-0.0.1-SNAPSHOT.jar"]