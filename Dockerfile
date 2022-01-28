FROM openjdk:11-jdk

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} demoproject.jar

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /demoproject.jar"]
