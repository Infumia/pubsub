plugins {
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    compileOnly(project(":codec"))

    compileOnly(libs.kotlin.reflect)
    compileOnly(libs.kotlinx.serialization.core)
    compileOnly(libs.kotlinx.serialization.protobuf)
}
