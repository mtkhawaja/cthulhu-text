#
# Download all dependencies & cache them
#
FROM maven:latest AS source
WORKDIR /opt/app/
COPY pom.xml ./pom.xml
RUN mvn dependency:go-offline
#
# Development (requires volume mounting for hot reloading)
#
FROM source AS development
WORKDIR /opt/app
COPY . ./
CMD mvn -Ddebug=true spring-boot:run