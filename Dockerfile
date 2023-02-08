#
# Download all dependencies & cache them
#
FROM maven:latest AS source
ENV APPLICATION_HOME=/opt/cthulhu-text/build/
WORKDIR "${APPLICATION_HOME}"
COPY pom.xml ./pom.xml
RUN mvn dependency:resolve-plugins dependency:go-offline
#
# Build & Package
#
FROM source AS builder
WORKDIR "${APPLICATION_HOME}"
COPY src ./src
RUN mvn clean package spring-boot:repackage -Dspring.profiles.active="test"
#
# Install
#
FROM openjdk:17-alpine as production
WORKDIR /opt/cthulhu-text/bin
COPY --from=builder /opt/cthulhu-text/build/target/api-*.jar ./ctext.jar
CMD java -jar ctext.jar