# 개발 환경용 Dockerfile

# 베이스 이미지 설정 (Java 17을 사용) oracle
FROM eclipse-temurin:17-jdk-alpine

# 애플리케이션 JAR 파일 복사
COPY build/libs/*.jar ./app.jar

## 개발 환경 프로파일 설정
#ENV SPRING_PROFILES_ACTIVE=dev

# MySQL 환경 변수 설정 (개발 환경용)
ENV SPRING_DATASOURCE_URL=jdbc:mysql://katj-mysql:3306/katj
ENV SPRING_DATASOURCE_USERNAME=root
ENV SPRING_DATASOURCE_PASSWORD=katj4

# 포트 설정
EXPOSE 8080

# 애플리케이션 실행
CMD ["java", "-jar", "./app.jar"]