FROM openjdk:11-jre-slim
ENV HOST=localhost
COPY target/runescape-*-jar-with-dependencies.jar /runescape.jar
CMD java -jar /runescape.jar ${HOST} -Dorg.apache.activemq.SERIALIZABLE_PACKAGES=*