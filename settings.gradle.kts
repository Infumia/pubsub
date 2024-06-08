plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "pubsub"

include("common", "redis", "kotlin-extensions", "kotlin-protobuf")

project(":kotlin-extensions").projectDir = file("kotlin/extensions")
project(":kotlin-protobuf").projectDir = file("kotlin/protobuf")
