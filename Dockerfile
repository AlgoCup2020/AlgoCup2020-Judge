FROM openjdk:8-alpine

MAINTAINER Jacek Spolnik <jacek.spolnik@gmail.com>

WORKDIR /app

COPY build/libs/jalgoarena-judge-*.jar /app/
COPY build/classes/kotlin/main/com/jalgoarena/type/ /app/build/classes/kotlin/main/com/jalgoarena/type/

EXPOSE 5008

CMD ["/usr/bin/java", "-Xms1g", "-Xmx2g", "-jar", "/app/jalgoarena-judge-*.jar"]