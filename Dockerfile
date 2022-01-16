FROM openjdk:11-jdk

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} demoproject.jar

ENTRYPOINT ["java", "-Dspring.profiles.active=local", "-jar", "/demoproject.jar"]
