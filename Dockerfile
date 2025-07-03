# 1단계: Build Stage
FROM gradle:8.5-jdk21-alpine AS builder

# 캐시 활용을 위한 종속성 먼저 복사
COPY build.gradle.kts settings.gradle.kts ./
COPY gradle ./gradle
RUN gradle build --no-daemon || return 0

# 소스코드 복사 및 빌드
COPY . .
RUN gradle clean build --no-daemon

# 2단계: Runtime Stage
FROM amazoncorretto:21-alpine-jdk

# 빌드 결과 복사
COPY --from=builder /home/gradle/build/libs/*.jar app.jar

# 실행
ENTRYPOINT ["java", "-Dspring.profiles.active=prod,railway", "-jar", "app.jar"]
