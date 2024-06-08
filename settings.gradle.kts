rootProject.name = "pubsub"

include("util", "common", "redis", "kotlin", "kotlin-extensions", "kotlin-protobuf")

project(":kotlin-extensions").projectDir = file("kotlin/extensions")
project(":kotlin-protobuf").projectDir = file("kotlin/protobuf")
