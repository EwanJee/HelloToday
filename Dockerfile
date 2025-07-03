FROM amazoncorretto:21-alpine-jdk
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} hellotodayapi.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=prod, railway", "-jar", "hellotodayapi.jar"]