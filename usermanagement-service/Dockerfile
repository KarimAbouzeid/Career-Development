# Start with a base image that has JDK and Maven
FROM maven:3.8.4-openjdk-17 AS build

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml and install dependencies
COPY pom.xml .

# Copy the source code into the container
COPY src ./src

# Package the application into a JAR file
RUN mvn clean package -DskipTests

# Start a new image from OpenJDK for running the application
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the build stage
    COPY --from=build /app/target/usermanagement-service.jar ./app.jar

# Expose the port the application runs on (8080 by default)
EXPOSE 8080

# Set the command to run the JAR file
CMD ["java", "-jar", "app.jar"]
