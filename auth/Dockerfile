FROM eclipse-temurin:17-jdk-alpine
WORKDIR /auth
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /auth/auth.jar
ENTRYPOINT ["java","-jar","auth.jar"]