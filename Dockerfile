#
# Build stage
#
FROM maven:3.8.4-openjdk-17-slim AS mavenBuild
COPY src /app/src
COPY pom.xml /app
RUN mvn -f /app/pom.xml clean package

#
# Package stage
#
FROM openjdk:17-alpine
COPY --from=mavenBuild /app/target/api-*.jar /app.jar
CMD java -jar /app.jar --server.port=${PORT:-8080}