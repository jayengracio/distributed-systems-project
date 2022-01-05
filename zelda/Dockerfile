FROM openjdk:11-jre-slim
ENV HOST=localhost
COPY target/zelda-*-jar-with-dependencies.jar /zelda.jar
CMD java -jar /zelda.jar ${HOST} -Dorg.apache.activemq.SERIALIZABLE_PACKAGES=*