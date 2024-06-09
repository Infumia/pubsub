plugins {
    alias(libs.plugins.dokka)
}

dependencies {
    compileOnly(project(":common"))
}

tasks {
    javadocJar {
        from(dokkaJavadoc.map { it.outputs })
    }
}
