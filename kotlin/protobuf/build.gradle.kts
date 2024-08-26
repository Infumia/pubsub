import net.infumia.gradle.applyPublish

plugins { alias(libs.plugins.kotlin.serialization) }

applyPublish("kotlin-protobuf")

dependencies {
    compileOnly(project(":codec"))

    compileOnly(libs.kotlin.reflect)
    compileOnly(libs.kotlinx.serialization.core)
    compileOnly(libs.kotlinx.serialization.protobuf)
}
