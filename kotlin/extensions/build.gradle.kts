dependencies {
    compileOnly(project(":common"))
}

tasks {
    javadocJar {
        from(dokkaJavadoc.map { it.outputs })
    }
}
