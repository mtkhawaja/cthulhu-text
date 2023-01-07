#
# Download all dependencies & cache them
#
FROM maven:latest AS source
WORKDIR /opt/app/
COPY pom.xml ./pom.xml
RUN mvn dependency:go-offline
#
# Build
#
FROM source AS builder
WORKDIR /opt/app
COPY src ./src
RUN mvn clean install
#
# Package
#
FROM openjdk:17-alpine as production
WORKDIR /opt/app
COPY --from=builder /opt/app/target/api-*.jar /app.jar
CMD java -jar /app.jar