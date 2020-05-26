FROM openjdk:12.0.2
WORKDIR /app
COPY target/convenio-0.0.1-SNAPSHOT.jar .
EXPOSE 8185
ENTRYPOINT ["java", "-jar", "convenio-0.0.1-SNAPSHOT.jar"]