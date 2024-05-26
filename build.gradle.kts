
plugins {
    java
    application
    id("org.springframework.boot") version "2.7.12"
    id("io.spring.dependency-management") version "1.1.4"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("io.jsonwebtoken:jjwt:0.9.1")
    implementation("org.mariadb.jdbc:mariadb-java-client:3.3.3")
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

tasks {
    application {
        mainClass = "com.example.jwtspringsecurity.JwtSpringSecurityApplication"
    }

    compileJava {
        options.encoding = "UTF-8"
        sourceCompatibility = "1.8"
        targetCompatibility = "1.8"
    }
}