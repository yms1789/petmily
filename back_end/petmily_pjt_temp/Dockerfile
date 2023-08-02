# Use a base image with Java installed
FROM openjdk:17-oracle

# Expose the application's port
EXPOSE 8080

# Copy the application's jar file into the container
<<<<<<< HEAD
ARG JAR_FILE=petmily_pjt_temp/build/libs/*.jar
COPY ${JAR_FILE} app.jar
=======
#ARG JAR_FILE=build/libs/*.jar
COPY build/libs/*.jar app.jar
>>>>>>> d29eaf47333ce49f64a4375c2a0d60f5e296dfef

# Command to run the application when the container starts.
ENTRYPOINT ["java", "-jar", "/app.jar"]
