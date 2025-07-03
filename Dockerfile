# 1단계: Gradle을 사용해 빌드
FROM amazoncorretto:21-alpine-jdk AS builder
WORKDIR /app
COPY . .

ENV MONGO_URI MONGO_URI
ENV MONGOUSER MONGOUSER
ENV MONGOPASSWORD MONGOPASSWORD

RUN apk add --no-cache bash
RUN chmod +x ./gradlew
RUN ./gradlew clean build --no-daemon

# 2단계: 빌드된 JAR 파일만 실행 컨테이너로 복사
FROM amazoncorretto:21-alpine-jdk
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-Dspring.profiles.active=prod, railway", "-Duser.timezone=Asia/Seoul", "-jar", "/app/app.jar"]