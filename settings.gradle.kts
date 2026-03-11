plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
rootProject.name = "meowth"

include("category-domain")

project(":category-domain").projectDir = file("category/domain")