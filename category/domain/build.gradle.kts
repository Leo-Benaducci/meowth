plugins {
    kotlin("jvm")
}

dependencies {
    implementation(libs.uuid.creator)
    testImplementation(libs.kotlin.test)
    testImplementation(libs.mockito.kotlin)
}

kotlin {
    jvmToolchain(25)
}

tasks.test {
    useJUnitPlatform()
}