FROM openjdk:11-jre-slim
ENV HOST=localhost
COPY target/cyberpunk-*-jar-with-dependencies.jar /cyberpunk.jar
CMD java -jar /cyberpunk.jar ${HOST} -Dorg.apache.activemq.SERIALIZABLE_PACKAGES=*