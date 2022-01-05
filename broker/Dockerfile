FROM openjdk:11-jre-slim
ENV HOST=localhost
COPY target/broker-*-jar-with-dependencies.jar /broker.jar
CMD java -jar /broker.jar ${HOST} -Dorg.apache.activemq.SERIALIZABLE_PACKAGES=*