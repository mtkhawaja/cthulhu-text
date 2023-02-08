#
# Download all dependencies & cache them
#
FROM maven:latest AS source
ENV APPLICATION_HOME=/opt/cthulhu-text/dev/
WORKDIR "${APPLICATION_HOME}"
COPY pom.xml ./pom.xml
RUN mvn dependency:go-offline
#
# Development (requires volume mounting for hot reloading)
#
FROM source AS development
WORKDIR "${APPLICATION_HOME}"
COPY . ./
CMD mvn -Ddebug=true spring-boot:run