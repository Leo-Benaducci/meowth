plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    implementation(project(":shared"))
    implementation(libs.uuid.creator)
    testImplementation(libs.kotlin.test)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.junit.jupiter.params)
}

kotlin {
    jvmToolchain(25)
}

tasks.test {
    useJUnitPlatform()
}