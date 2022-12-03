import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.7.6"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    kotlin("jvm") version "1.7.21"
    kotlin("plugin.spring") version "1.6.21"
}

group = "rs.skurikhin"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.projectlombok:lombok")
    implementation("com.zaxxer:HikariCP:4.0.3")
    implementation("org.postgresql:postgresql:42.2.25")
    implementation("org.springframework.boot:spring-boot-starter-web")

    // jpa
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")


    testImplementation(kotlin("test"))
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}