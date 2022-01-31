FROM openjdk:11-jdk

ARG JAR_FILE=build/libs/SangHyun-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} demoproject.jar

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /demoproject.jar"]
