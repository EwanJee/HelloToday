# 1단계: Build Stage
FROM gradle:8.5-jdk21-alpine AS builder

# 작업 디렉토리 설정
WORKDIR /app

# gradlew 및 gradle 관련 파일 먼저 복사
COPY gradlew ./
COPY gradle ./gradle
COPY build.gradle.kts settings.gradle.kts ./

# 종속성 캐싱을 위한 초기 빌드
RUN ./gradlew build --no-daemon || return 0

# 전체 소스 복사 및 빌드
COPY . .
RUN ./gradlew clean build --no-daemon

# 2단계: Runtime Stage
FROM amazoncorretto:21-alpine-jdk

# 작업 디렉토리 설정
WORKDIR /app

# 빌드 결과 복사
COPY --from=builder /app/build/libs/*.jar app.jar

# 애플리케이션 실행
ENTRYPOINT ["java", "-Dspring.profiles.active=prod,railway", "-jar", "app.jar"]
