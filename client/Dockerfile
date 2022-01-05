FROM openjdk:11-jre-slim
ENV HOST=localhost
COPY target/client-*-jar-with-dependencies.jar /client.jar
CMD java -jar /client.jar ${HOST}