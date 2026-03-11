plugins {
    alias(libs.plugins.kotlin.jvm) apply false
}

allprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")

    group = "br.com.lbenaducci.meowth"
    version = "0.0.1"

    repositories {
        mavenCentral()
    }
}