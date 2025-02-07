FROM ghcr.io/graalvm/jdk-community:17


COPY src/main/resources/config.yml /app/config.yml
COPY target/reco-core-1.0-SNAPSHOT.jar /app/app.jar

CMD ["java","--add-opens=java.base/java.lang=ALL-UNNAMED","-jar","/app/app.jar","server", "/app/config.yml"]
