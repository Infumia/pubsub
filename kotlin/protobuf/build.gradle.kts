plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    compileOnly(project(":common"))

    compileOnly(libs.kotlin.reflect)
    compileOnly(libs.kotlinx.serialization.core)
    compileOnly(libs.kotlinx.serialization.protobuf)
}
