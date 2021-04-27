FROM openjdk:11
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} ms-address-book.jar
ENTRYPOINT ["java","-jar","/ms-address-book.jar"]