plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.dokka)
    id("net.infumia.pubsub.build.publishing")
}

dependencies {
    compileOnly(project(":common"))
}

tasks {
    javadocJar {
        from(dokkaJavadoc.map { it.outputs })
    }
}
