# Build stage
FROM eclipse-temurin:23-jdk AS build

# Install Maven
ARG MAVEN_VERSION=3.9.6
RUN apt-get update && apt-get install -y curl \
  && curl -fsSL https://dlcdn.apache.org/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz | tar xz -C /opt \
  && ln -s /opt/apache-maven-${MAVEN_VERSION}/bin/mvn /usr/bin/mvn

WORKDIR /app

# Copy everything to /app
COPY . .

# Build the Spring Boot jar
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:23-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
