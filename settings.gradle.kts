plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
rootProject.name = "meowth"

include("shared")
include("category-domain")
include("category-application")
include("infrastructure")

project(":category-domain").projectDir = file("category/domain")
project(":category-application").projectDir = file("category/application")