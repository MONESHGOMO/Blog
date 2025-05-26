# Use Maven with JDK 23 to build the app
FROM maven:3.9.0-eclipse-temurin-23 AS build

# Copy your project files
COPY . .

# Build the Spring Boot jar, skipping tests for faster build
RUN mvn clean package -DskipTests

# Use JDK 23 runtime image to run the app
FROM eclipse-temurin:23-jre-alpine

# Copy the built jar from the build stage
COPY --from=build /target/*.jar app.jar

# Expose port 8080 (default Spring Boot port)
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "/app.jar"]
