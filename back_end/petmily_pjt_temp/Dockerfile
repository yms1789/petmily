# Use a base image with Java installed
FROM openjdk:17-oracle

# Expose the application's port
EXPOSE 8080

# Copy the application's jar file into the container
ARG JAR_FILE=petmily_pjt_temp/build/libs/*.jar
COPY ${JAR_FILE} app.jar

# Command to run the application when the container starts.
ENTRYPOINT ["java", "-jar", "/app.jar"]
