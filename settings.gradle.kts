plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "pubsub"

include("codec", "common", "redis", "jackson", "kotlin-extensions", "kotlin-coroutines", "kotlin-protobuf")

project(":kotlin-extensions").projectDir = file("kotlin/extensions")
project(":kotlin-coroutines").projectDir = file("kotlin/coroutines")
project(":kotlin-protobuf").projectDir = file("kotlin/protobuf")
