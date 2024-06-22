import net.infumia.gradle.publish

publish()

dependencies {
    compileOnly(project(":codec"))

    compileOnly(libs.caffeine)
}
