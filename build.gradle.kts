plugins {
	application
	id("org.springframework.boot") version "3.1.4"
	id("io.spring.dependency-management") version "1.0.15.RELEASE"
	id("io.freefair.lombok") version "8.1.0"
	id("java")
	kotlin("jvm") version "1.9.10"
	kotlin("plugin.spring") version "1.9.10"
	kotlin("plugin.lombok") version "1.9.10"
}

group = "com.grishin"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_19
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
	maven { url = uri("https://repo.spring.io/milestone") }
	maven { url = uri("https://repo.spring.io/snapshot") }
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive:3.0.4")
	implementation("org.springframework.boot:spring-boot-starter-webflux:3.0.4")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.14.2")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions:1.2.2")
	implementation("org.jetbrains.kotlin:kotlin-reflect:1.8.20-RC")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.7.1")
	implementation("org.camunda.community.extension.kotlin.coworker:coworker-core:0.5.0")
	implementation("com.github.piomin:reactive-logstash-logging-spring-boot-starter:1.4.1")
	implementation("io.camunda:zeebe-client-java:8.2.15")
	implementation("org.camunda.community.extension.kotlin.coworker:coworker-spring-boot-starter:0.5.0")
	implementation("org.apache.commons:commons-lang3:3.12.0")
	implementation("io.camunda:zeebe-process-test-extension:8.2.15")
	implementation("org.springframework.boot:spring-boot-starter-actuator:3.1.4")
	compileOnly("org.projectlombok:lombok:1.18.26")
	annotationProcessor("org.projectlombok:lombok:1.18.26")
	testImplementation("org.springframework.boot:spring-boot-starter-test:3.1.0")
	testImplementation("io.projectreactor:reactor-test:3.5.4")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
