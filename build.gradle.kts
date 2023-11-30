plugins {
	java
	id("org.springframework.boot") version "3.1.5"
	id("io.spring.dependency-management") version "1.1.3"
	id("com.bmuschko.docker-spring-boot-application") version "9.3.6"
}

group = "com.midgetspinner31"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

docker {
	springBootApplication {
		baseImage = "openjdk:17-alpine"
		ports = listOf(8080)
	}
}

repositories {
	mavenCentral()
}

val mongockVersion = "5.3.4"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.security:spring-security-data")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("io.mongock:mongock-springboot-v3:$mongockVersion")
	implementation("io.mongock:mongodb-springdata-v4-driver:$mongockVersion")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")
	implementation("com.github.therapi:therapi-runtime-javadoc:0.15.0")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	annotationProcessor("com.github.therapi:therapi-runtime-javadoc-scribe:0.15.0")
	annotationProcessor("org.projectlombok:lombok")
}
