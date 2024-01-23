import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.1.3"
	id("io.spring.dependency-management") version "1.1.3"
	kotlin("jvm") version "1.8.22"
	kotlin("plugin.spring") version "1.8.22"
	kotlin("plugin.jpa") version "1.8.22"
	jacoco
}

group = "com.hh2"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

extra["testcontainersVersion"] = "1.19.1"


dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.springframework.boot:spring-boot-starter-aop")

	// 로그백 의존성
//	implementation("io.github.microutils:kotlin-logging:1.12.5")
	implementation(group = "ca.pjer", name = "logback-awslogs-appender", version = "1.6.0")

	// 프로퍼티 제어 in xml
	implementation("org.codehaus.janino:janino:3.1.10")

	// 스케줄링 처리를 위한 코루틴 라이브러리 추가
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0")
	
	// Retry 추가
	implementation("org.springframework:spring-aspects")
	implementation("org.springframework.retry:spring-retry")

	runtimeOnly("com.mysql:mysql-connector-j")
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	// testContainer
	testImplementation ("org.testcontainers:testcontainers:1.19.1") // TC 의존성
	testImplementation ("org.testcontainers:junit-jupiter:1.19.1")  // TC 의존성
	testImplementation ("org.testcontainers:mysql:1.19.1")     // MtSQL 컨테이너 사용

	//rest-assured
	testImplementation ("io.rest-assured:kotlin-extensions:5.3.2")

	// circuit breaker
	implementation("io.github.resilience4j:resilience4j-spring-boot3:2.0.2")
	implementation("io.github.resilience4j:resilience4j-circuitbreaker:2.0.2")
	implementation("io.github.resilience4j:resilience4j-retry:2.0.2")
	implementation("io.github.resilience4j:resilience4j-timelimiter:2.0.2")
	implementation("io.github.resilience4j:resilience4j-test:2.0.2")
	implementation("org.springframework.boot:spring-boot-starter-actuator")

}

dependencyManagement {
	imports {
		mavenBom("org.testcontainers:testcontainers-bom:${property("testcontainersVersion")}")
	}
}


tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

tasks.named<Jar>("jar") {
	enabled = false
}

tasks.withType<Test> {
	useJUnitPlatform()
}

jacoco {
	// JaCoCo 버전
	toolVersion = "0.8.8"
}


tasks.test{
	extensions.configure(JacocoTaskExtension::class) {
		isEnabled = true
		destinationFile = file("$buildDir/jacoco/jacoco.exec")
	}
	finalizedBy("jacocoTestReport") // 테스트가 실행된 후에 JaCoCo 보고서 생성
}

tasks.jacocoTestReport {
	reports {
		csv.required.set(true)
		xml.required.set(true) // XML 형식의 보고서 생성
		html.required.set(true) // HTML 형식의 보고서 생성
	}
}


tasks.jacocoTestCoverageVerification {
	violationRules {
		rule {
			element = "CLASS"

			limit {
				counter = "BRANCH"
				value = "COVEREDRATIO"
				minimum = "0.90".toBigDecimal()
			}
		}
	}
}