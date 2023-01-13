FROM openjdk:17

ARG JAR_FILE=target/student-services-0.0.1-SNAPSHOT.jar

COPY target/student-services-0.0.1-SNAPSHOT.jar student-services-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} student-services-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","student-services-0.0.1-SNAPSHOT.jar"]