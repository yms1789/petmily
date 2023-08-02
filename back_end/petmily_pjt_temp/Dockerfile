FROM openjdk:17-oracle
EXPOSE 8080
ARG JAR_FILE=/var/lib/jenkins/workspace/petmily-build/back_end/petmily_pjt_temp/build/libs/petmily-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "./app.jar"]