FROM maven:3.6.3-adoptopenjdk-11 as builder

# speed up Maven JVM a bit
ENV MAVEN_OPTS="-XX:+TieredCompilation -XX:TieredStopAtLevel=1"

# set working directory
WORKDIR /opt/demo

# copy just pom.xml
COPY pom.xml .

# go-offline using the pom.xml
RUN mvn dependency:go-offline

# copy your other files
COPY src ./src

# compile the source code and package it in a jar file
RUN mvn clean install -Dmaven.test.skip=true

FROM adoptopenjdk/openjdk11:jre-11.0.9_11-alpine

ENV WORKDIR /opt/app
WORKDIR $WORKDIR

EXPOSE 8080

COPY --from=builder /opt/demo/target/*.jar $WORKDIR/app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
