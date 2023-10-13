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


## 📌 우리들의 PR규칙
# Git Flow
<img width="698" alt="image" src="https://github.com/KATJ-HH2/katj/assets/38535971/ec400cf4-1858-41c9-9db9-3705ef46b726">

### Git Flow 한계
  > Git-Flow는 등장하고 10년 넘게 표준처럼 자리잡고, 더 나아가 마치 만병통치약처럼 사용되었다. 현재는 **Git으로 관리되는 인기있는 유형의 소프트웨어가 웹 어플리케이션으로 이동**하고 있다. **웹 어플리케이션은 일반적으로 롤백되지 않고, 지속적으로 제공(Continuous Delivery)되므로 여러 버전의 소프트웨어를 지원할 필요가 없다**.
  > 그러나 명시적으로 버전을 관리해야하는 소프트웨어를 개발한다면, 여전히 Git Flow가 적합할 수 있다.

이러한 이유가 있지만, Git Flow의 흐름 및 전체적인 구조를 파악하는데 좋은 레퍼런스가 되며, 대부분의 기업과 우리는 Git Flow가 익숙하기 때문에 `GitHub Flow` 보다는 `Git Flow` 를 사용하고 있어요.


## 그럼 어떻게 사용하고있나요?
각 개발 메인 브랜치는 Dev, Prod를 메인으로 두고 있어요. 이 두개의 브랜치에서 Feature 브랜치가 생성되요. 각 Feature 브랜치는 기능 기준으로 만들어집니다. 
  Ex) 사용자 등록 기능을 만들면 `feature/lil/createAddUser` 식으로 만들게 되죠.
- 그리고 각 feature 브랜치를 생성하고 기능 개발이 끝나면 PR( Pull Request)를 생성하여, 팀원들의 코드리뷰를 받게됩니다.
- feature 브랜치의 코드리뷰가 끝나면 Dev 브랜치로 Merge가 되고, Dev 브랜치는 Prod로 Push를 하게 되는거죠.


## 우리들의 규칙
#### [ GitHub 템플릿으로 Issue & Feature Request 내용 규격화]
- 언제든지 생길 수 있는 다양한 버그들에 대해서 서로 알기 쉽게 알아볼 수 있도록 했어요.
```
## 버그 설명

[버그에 대한 명확하고 간결한 설명]

## 재현 단계

[버그를 재현하기 위한 단계]

## 예상 동작

[예상했던 동작과 실제 동작의 차이]
```
- 각자의 기능 브랜치에서 작업하지만 필요에 따라서 요청시 규격화된 이슈를 남기기로 했어요.
```
## 기능 제안

[제안하는 기능에 대한 명확하고 간결한 설명]

## 예상 동작

[새로운 기능이 추가되면 어떻게 동작해야 하는지 설명]

## 추가 정보

[기능 제안에 대한 추가적인 정보나 컨텍스트]
```
#### [ GitHub 템플릿으로 PR내용 규격화 ]
- 우리는 중구난방의 PR 메세지 및 아주 커다란 커밋의 크기를 정하기로 했어요.
##### PR 템플릿
```
# 해결하려는 문제가 무엇인가요?
*

# 어떻게 해결했나요?
* 

## Attachment
```

<details>
  <summary>[ Commit Prefix에 특정단어 사용 ] </summary>
  <div markdown="1">
    - commit 메세지에 Prefix 단어를 사용해서 해당 커밋의 내용을 미리 알 수 있게끔 사용하고 있어요.
    <img width="701" alt="image" src="https://github.com/KATJ-HH2/katj/assets/38535971/2c232c79-8a7c-4072-aefe-7d1e91fafbf5">
  </div>
</details>

<details>
  <summary>[ PR도 최대한 작은 단위로 쪼개서 올리기 ]</summary>
  <div markdown="1">
    - 사실 코드리뷰는 리뷰어의 입장이 제일 힘들어요. 
      본인 개발이 바쁘지만 팀원들, 우리 프로젝트의 개발 품질을 위해서 본인의 시간을 빼서 리뷰를 해주기 때문이죠. 
      그렇기 때문에 리뷰어가 리뷰를 하기 편하게 작은 단위의 Commit으로 올려 코드리뷰를 편하게 하는게 중요해요. (내가 리뷰어가 될 수 도있기 때문이죠..?) 
    <img width="675" alt="image" src="https://github.com/KATJ-HH2/katj/assets/38535971/7289159e-637c-4f7b-a70b-854146f5c509">
  </div>
</details>

<details>
  <summary>[ Merge는 적어도 하나의 Approve가 있어야해 ]</summary>
  <div markdown="1">
    - 우리는 정말.. 쉽지않은 규칙을 두었어요. 적어도 한 개의 Approve가 있어야 Merge를 할 수 있도록 설정해놓았어요. 
      이렇게 하는 이유는 리뷰어가 코드리뷰의 경각심을 깨우기 위함도 있지만, 바쁘다고 막 Merge를 하지 않기 위함을 방지하는것도 있어요.
      <img width="672" alt="image" src="https://github.com/KATJ-HH2/katj/assets/38535971/1297632f-810f-4763-a715-97e82eb1b39e">
  </div>
</details>

## 🏃Getting started in the local environment

### How to run
1. 반드시 기기에 자바와 코틀린이 설치되어 있는지 확인해주세요.
2. 이 리파지토리를 복제해 주세요:
```bash
git clone https://github.com/KATJ-HH2/katj.git
```
### Navigate to the project directory
```bash
cd KATJ-HH2
```
### Build the project
```bash
./gradlew build
```
### Run the application
```bash
./gradlew run
```
### Open your web browser and go to [헬스체크 url](http:localhost:8080/hello)



## 📌 Feature offered
### <택시 드라이버 관점>
- EndPoint: POST ‘/taxidrivers/info’
</br>Function: 택시기사 정보와 택시 정보를 등록합니다

- EndPoint:
- GET ‘/taxidrivers/status’
- PUT ‘/taxidrivers/status’
</br>Function: 택시 운행 상태를 변경하거나 조회합니다

- EndPoint: GET ‘/trip/info’
</br>Function: 운행 예상경로와 요금 정보를 조회합니다

#### trip 테이블 상태 변화
- 사용자가 호출 요청 (수동 호출)
   TripController.userCallTaxi(kakaoAPI 조회 반환값, 사용자id) -> trip 생성, TripStatus.CALL_TAXI
- 택시 (랜덤) 배정 (자동 호출)
   TripStatus.CALL_TAXI -> 운행 가능 택시 랜덤 조회(없더라도 5초간 지속 조회) -> 사용자에게 택시 랜덤 배정 --> TripStatus.ASSIGN_TAXI(TaxiDriver.WAITING -> ASSIGNMENT)
- 보류 - 사용자가 호출 취소(opt)
  보류 - 택시기사가 호출 취소(opt)
- 택시 운행 중 (수동 호출)
  TripController.taxiDriveStart(tripId)  -> TripStatus.DRIVING(TaxiDriver.START_DRIVING)
- 택시 운행 완료 (수동 호출)
  TripController.taxiDriverEnd(tripId)  -> TripStatus.END -> TaxiDriver.END_DRIVING -> TaxiDriver.WAITING
- 사용자에게 결제 요청 -> TripStatus.END -> 사용자 결제 로직


- EndPoint: GET ‘/trip/totalfare’
</br>Function: 당일 운임료 합계를 조회합니다

### <사용자 관점>
#### Kakao API 사용으로 위치 및 택시 정보 확인
- EndPoint: POST ‘/location’
</br>Function: 검색어로 주소 정보 조회 및 이력을 저장합니다.

- EndPoint: POST '/route'
</br>Function: 출/도 주소 정보로 요금 정보 조회 및 이력을 저장합니다.

#### 사용자 즐겨찾기 관리
- EndPoint: GET ‘/favorite?userId=’
</br>Function: 사용자의 모든 즐겨찾기를 조회합니다

- EndPoint: GET ‘/{favoriteId}?userId=’
</br>Function: 사용자가 해당 즐겨찾기를 조회합니다.

- EndPoint: POST ‘/favorite?userId=’
</br>Function: 사용자가 즐겨찾기를 추가합니다.

- EndPoint: PUT ‘/{favoriteId}?userId’
</br>Function: 사용자가 해당 즐겨찾기를 수정합니다.

- EndPoint: DELETE ‘/{favoriteId}?userId’
</br>Function: 사용자가 해당 즐겨찾기를 삭제합니다.

- EndPoint: DELETE ‘/delete-multi?userId’
</br>Function: 사용자가 여러개의 즐겨찾기를 삭제합니다.

- EndPoint: DELETE ‘/delete-all?userId’
</br>Function: 사용자의 모든 즐겨찾기를 삭제합니다.

### 사용자 계좌 / 카드 등록
- EndPoint: GET ‘/payment-method/{paymentMethodId}?userId=’
</br> Function: 사용자가 해당 결제 수단을 조회합니다.

- EndPoint: GET ‘/payment-method?userId=’
</br>Function: 사용자가 모든 결제 수단을 조회합니다.

- EndPoint: POST ‘/payment-method/bank-account?userId=’
</br>Function: 사용자가 은행 계좌를 결제 수단으로 등록합니다.

- EndPoint: POST ‘/payment-method/card?userId=’
</br>Function: 사용자가 카드를 결제 수단으로 등록합니다.

- EndPoint: POST ‘/payment-method/valid/bank-account?userId=’
</br>Function: 사용자가 등록 하려는 계좌의 유효성을 외부 API를 통해 인증합니다.

- EndPoint: POST ‘/payment-method/valid/card?userId=’
</br>Function: 사용자가 등록 하려는 카드의 유효성을 외부 API를 통해 인증합니다.

- EndPoint: DELETE ‘/payment-method/{paymentMethodId}’
</br>Function: 사용자가 등록된 결제 수단을 하나 삭제합니다.

- EndPoint: DELETE ‘/payment-method’
</br>Function: 사용자가 등록된 결제 수단을 모두 삭제합니다.



## 📑 Table design
![Blank diagram](https://github.com/KATJ-HH2/katj/assets/87371627/557bcdc6-25cf-4840-b39b-9ea157cf27f8)



## 🤝Co-operation with JIRA
#### [ Jira를 활용하여 팀별 회의 후에도 애자일 방법론에 따라 신속한 기능 구현 및 변경을 진행하였어요 ]
![image](https://github.com/KATJ-HH2/katj/assets/87371627/71ce3ea4-bfe6-4daa-a686-1d1694097120)

