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
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.springframework.boot:spring-boot-starter-aop")
	runtimeOnly("com.mysql:mysql-connector-j")
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	// testContainer
	testImplementation ("org.testcontainers:testcontainers:1.19.1") // TC 의존성
	testImplementation ("org.testcontainers:junit-jupiter:1.19.1")  // TC 의존성
	testImplementation ("org.testcontainers:mysql:1.19.1")     // MtSQL 컨테이너 사용
	// testImplementation ("org.testcontainers:jdbc:1.16.0")           // DB와의 JDBC connection
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

//  테스트결과 리포트를 저장할 경로 변경
//  default는 "${project.reporting.baseDir}/jacoco"
//  reportsDir = file("$buildDir/customJacocoReportDir")
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