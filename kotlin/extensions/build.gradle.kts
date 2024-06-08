plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.dokka)
    id("tr.com.infumia.pubsub.build.publishing")
}

dependencies {
    compileOnly(project(":common"))
}

tasks {
    javadocJar {
        from(dokkaJavadoc.map { it.outputs })
    }
}
