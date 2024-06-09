plugins {
    alias(libs.plugins.dokka)
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
