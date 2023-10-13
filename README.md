# SpringBoot-Project-Taxi-Scenario
SpringBoot + Kotlin taxi scenario

## 📂 Proejct Introduction
- Kakao 및 Kakao mobility API 연동으로 조회한 정보를 사용하여
  택시기사/사용자 관점에서 택시 호출/배정/결제 등의 기능을 구현한 택시 애플리케이션 프로젝트

## ⏲️ Development Period
- Sept.16th.2023 ~ Oct.13th.2023

## ⚙️ Development Environment
- Spring Boot : 3.1.3
    - spring-boot-starter-data-jpa
    - spring-boot-starter-web
    - spring-boot-starter-validation
    - jackson-module-kotlin
    - kotlin-reflect
    - spring-boot-starter-aop
    - spring-boot-starter-test
    - testcontainers:1.19.1 TC 의존성
    - mysql:1.19.1 MySQL
- Gradle : 8.2.1
- Test tool : JUnit5
- Kotlin : 1.8.22
- JAVA : corretto 17 ver.
- ORM : JPA (Hibernate), KotlinJDSL
- DB : RDS (MySQL)
- Infra : Docker, AWS ECR, ECS, FARGATE
- VCS : Git / GitHub
- IDEA : IntelliJ
- Cooperation tool : JIRA & SLACK & Gather

## 📌 Feature offered

### <택시 드라이버 관점>
- EndPoint: POST ‘/taxidrivers/info’
Function: 택시기사 정보와 택시 정보를 등록합니다

- EndPoint:
- GET ‘/taxidrivers/status’
- PUT ‘/taxidrivers/status’
Function: 택시 운행 상태를 변경하거나 조회합니다

- EndPoint: GET ‘/trip/info’
Function: 운행 예상경로와 요금 정보를 조회합니다

- EndPoint: GET ‘/trip/totalfare’
Function: 당일 운임료 합계를 조회합니다

### <사용자 관점>
#### Kakao API 사용으로 위치 및 택시 정보 확인
- EndPoint: POST ‘/location’
Function: 검색어로 주소 정보 조회 및 이력을 저장합니다.

- EndPoint: POST '/route'
Function: 출/도 주소 정보로 요금 정보 조회 및 이력을 저장합니다.

#### 사용자 즐겨찾기 관리
- EndPoint: GET ‘/favorite?userId=’
Function: 사용자의 모든 즐겨찾기를 조회합니다

- EndPoint: GET ‘/{favoriteId}?userId=’
Function: 사용자가 해당 즐겨찾기를 조회합니다.

- EndPoint: POST ‘/favorite?userId=’
Function: 사용자가 즐겨찾기를 추가합니다.

- EndPoint: PUT ‘/{favoriteId}?userId’
Function: 사용자가 해당 즐겨찾기를 수정합니다.

- EndPoint: DELETE ‘/{favoriteId}?userId’
Function: 사용자가 해당 즐겨찾기를 삭제합니다.

- EndPoint: DELETE ‘/delete-multi?userId’
Function: 사용자가 여러개의 즐겨찾기를 삭제합니다.

- EndPoint: DELETE ‘/delete-all?userId’
Function: 사용자의 모든 즐겨찾기를 삭제합니다.

### 사용자 계좌 / 카드 등록
- EndPoint: GET ‘/payment-method/{paymentMethodId}?userId=’
Function: 사용자가 해당 결제 수단을 조회합니다.

- EndPoint: GET ‘/payment-method?userId=’
Function: 사용자가 모든 결제 수단을 조회합니다.

- EndPoint: POST ‘/payment-method/bank-account?userId=’
Function: 사용자가 은행 계좌를 결제 수단으로 등록합니다.

- EndPoint: POST ‘/payment-method/card?userId=’
Function: 사용자가 카드를 결제 수단으로 등록합니다.

- EndPoint: POST ‘/payment-method/valid/bank-account?userId=’
Function: 사용자가 등록 하려는 계좌의 유효성을 외부 API를 통해 인증합니다.

- EndPoint: POST ‘/payment-method/valid/card?userId=’
Function: 사용자가 등록 하려는 카드의 유효성을 외부 API를 통해 인증합니다.

- EndPoint: DELETE ‘/payment-method/{paymentMethodId}’
Function: 사용자가 등록된 결제 수단을 하나 삭제합니다.

- EndPoint: DELETE ‘/payment-method’
Function: 사용자가 등록된 결제 수단을 모두 삭제합니다.
