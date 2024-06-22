import net.infumia.gradle.publish

publish("jackson")

dependencies {
    compileOnly(project(":codec"))

    compileOnly(libs.jackson)
}
