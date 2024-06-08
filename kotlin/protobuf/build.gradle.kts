plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.dokka)
    id("tr.com.infumia.pubsub.build.publishing")
}

dependencies {
    compileOnly(project(":common"))

    compileOnly(libs.kotlin.reflect)
    compileOnly(libs.kotlinx.serialization.core)
    compileOnly(libs.kotlinx.serialization.protobuf)
}

tasks {
    javadocJar {
        from(dokkaJavadoc.map { it.outputs })
    }
}
