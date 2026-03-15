plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
}

group = "br.com.lbenaducci.meowth.infrastructure"
version = "0.0.1"

tasks.bootJar {
    archiveFileName.set("meowth-app-${project.version}.jar")
}

dependencies {
    implementation(project(":category-application"))
    implementation(project(":category-domain"))
    implementation(project(":shared"))
    implementation(libs.spring.boot.starter.webmvc)
    implementation(libs.spring.boot.starter.actuator)
    implementation(libs.spring.boot.starter.data.mongodb)
    implementation(libs.kotlin.reflect)
    implementation(libs.jackson.module.kotlin)

    testImplementation(libs.kotlin.test)
    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.spring.boot.testcontainers)
    testImplementation(libs.testcontainers.junit.jupiter)
    testImplementation(libs.testcontainers.mongodb)
}

kotlin {
    jvmToolchain(21)
    compilerOptions {
        freeCompilerArgs.addAll(
            "-Xjsr305=strict",
            "-Xannotation-default-target=param-property"
        )
    }
}

tasks.test {
    useJUnitPlatform()
}