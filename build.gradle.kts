plugins {
    kotlin("jvm") version "2.3.0"
}

group = "br.com.lbenaducci"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(25)
}

tasks.test {
    useJUnitPlatform()
}