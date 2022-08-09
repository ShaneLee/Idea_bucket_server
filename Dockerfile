FROM maven:3.8.3-openjdk-17-slim as builder

WORKDIR /app
COPY pom.xml /app
COPY src ./src
COPY checkstyle.xml /app
COPY lombok.config /app

RUN --mount=type=cache,target=/root/.m2/repository \
  mvn -U dependency:resolve-plugins dependency:go-offline \
  -Dmaven.artifact.threads=12
RUN --mount=type=cache,target=/root/.m2/repository \ 
  mvn clean package -DskipTests

FROM openjdk:17.0.1-slim as runner
EXPOSE 8080

WORKDIR /app
RUN apt-get update &&  apt-get -y install curl jq && \
   groupadd -r java-user && \
   useradd -r -M -u 1002 -g java-user java-user && \
   chmod a=rwx,o+t /tmp

USER java-user

ARG JAR=/app/target/*-exec.jar
COPY --chown=1001:1001 --from=builder ${JAR} service.jar
ENTRYPOINT java ${JAVA_OPTS} -jar service.jar -Djava.security.egd=file:/dev/./urandom

HEALTHCHECK NONE

LABEL org.label-schema.author="Shane Lee"
