import net.infumia.gradle.publish

plugins { alias(libs.plugins.kotlin.serialization) }

publish("kotlin-protobuf")

dependencies {
    compileOnly(project(":codec"))

    compileOnly(libs.kotlin.reflect)
    compileOnly(libs.kotlinx.serialization.core)
    compileOnly(libs.kotlinx.serialization.protobuf)
}
