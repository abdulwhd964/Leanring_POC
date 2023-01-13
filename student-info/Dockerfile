FROM openjdk:17

ARG JAR_FILE=target/student-info-0.0.1-SNAPSHOT.jar

COPY target/student-info-0.0.1-SNAPSHOT.jar student-info-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} student-info-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","student-info-0.0.1-SNAPSHOT.jar"]