# ubuntu currently required by Microsoft Speech SDK
FROM ubuntu:xenial
ARG DEPENDENCY=target/dependency
RUN apt-get update
RUN apt-get install -y openjdk-8-jre
# packages needed for Microsoft Speech SDK, as documented bu Microsoft
RUN apt-get install -y build-essential libssl1.0.0 libcurl3 libasound2 wget
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app
EXPOSE 8080
CMD ["java","-cp","app:app/lib/*","de.sttsample.ApplicationKt"]

