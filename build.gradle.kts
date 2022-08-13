import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.7.0"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
}

group = "ru.dankos.api"
java.sourceCompatibility = JavaVersion.VERSION_17

extra["springCloudVersion"] = "2021.0.3"

repositories {
    mavenCentral()
    jcenter()
    maven { url = uri("https://dl.bintray.com/konrad-kaminski/maven") }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("com.github.ben-manes.caffeine:caffeine")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.apache.kafka:kafka-streams")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springframework.kafka:spring-kafka")
    implementation("com.github.ben-manes.caffeine:caffeine")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")
    implementation("org.springframework.cloud:spring-cloud-starter-loadbalancer")
    implementation("org.springframework.cloud:spring-cloud-starter-bootstrap")

    implementation("org.springframework.cloud:spring-cloud-starter-kubernetes-client-all")
    implementation("com.playtika.reactivefeign:feign-reactor-spring-cloud-starter:3.2.3")
    implementation("org.springframework.cloud:spring-cloud-starter-loadbalancer")
    implementation("io.github.microutils:kotlin-logging:2.1.23")
    implementation("org.springframework.kotlin:spring-kotlin-coroutine:0.3.7")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.6.4")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions:1.1.7")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.cloud:spring-cloud-contract-wiremock")
    testImplementation("org.springframework.kafka:spring-kafka-test")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")

    developmentOnly("org.springframework.boot:spring-boot-devtools")
}

configurations {
    testImplementation {
        exclude(group = "org.springframework.cloud", module = "spring-cloud-starter-config")
        exclude(group = "org.springframework.cloud", module = "spring-cloud-starter-netflix-eureka-client")
    }
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
