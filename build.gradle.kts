plugins {
	application
	id("org.springframework.boot") version "3.1.4"
	id("io.spring.dependency-management") version "1.0.15.RELEASE"
	id("io.freefair.lombok") version "8.1.0"
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
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
	implementation("org.camunda.community.extension.kotlin.coworker:coworker-core:0.5.0")
	implementation("com.github.piomin:reactive-logstash-logging-spring-boot-starter:1.4.1")
	implementation("io.camunda:zeebe-client-java:8.2.15")
	implementation("org.camunda.community.extension.kotlin.coworker:coworker-spring-boot-starter:0.5.0")
	implementation("org.apache.commons:commons-lang3")
	implementation("io.camunda:zeebe-process-test-extension:8.2.15")
	implementation("org.springframework.boot:spring-boot-starter-actuator:3.1.4")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")
}

tasks.withType<Jar> {
	enabled = true
	isZip64 = true
	duplicatesStrategy = DuplicatesStrategy.EXCLUDE
	manifest.attributes["Main-Class"] = "com.grishin.camunda.CamundaApplicationKt"

	from(sourceSets.main.get().output)
	dependsOn(configurations.runtimeClasspath)
	//from(sourceSets.main.get().resources)
	//from({
	//	configurations.compileClasspath.get().filter {
	//		it.name.endsWith("jar")
	//	}.map { zipTree(it) }
	//}) {
	//	exclude("META-INF/*.RSA", "META-INF/*.SF", "META-INF/*.DSA")
	//}
	from({
		configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
	})

}

tasks.withType<Test> {
	useJUnitPlatform()
}
