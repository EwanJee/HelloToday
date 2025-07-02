FROM amazoncorretto:21-alpine-jdk

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} hellotodayapi.jar

ENTRYPOINT sh -c 'java \
  -Dspring.profiles.active=prod,railway \
  -Dspring.data.mongodb.uri=$MONGO_URI \
  -Dspring.data.mongodb.username=$MONGO_USERNAME \
  -Dspring.data.mongodb.password=$MONGO_PASSWORD \
  - echo " $MONGO_URI " \
  -jar hellotodayapi.jar'
