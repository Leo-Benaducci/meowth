plugins {
    kotlin("jvm")
}

dependencies {
    implementation(project(":category-domain"))
    implementation(project(":shared"))
    implementation(libs.slf4j.api)
    testImplementation(libs.kotlin.test)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.mockito.junit.jupiter)
}

kotlin {
    jvmToolchain(25)
}

tasks.test {
    useJUnitPlatform()
}