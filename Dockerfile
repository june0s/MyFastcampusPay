FROM openjdk:11-slim-stretch
EXPOSE 8080
# jar 파일명은 파라미터로 입력 받겠다.
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
