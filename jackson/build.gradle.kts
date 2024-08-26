import net.infumia.gradle.applyPublish

applyPublish("jackson")

dependencies {
    compileOnly(project(":codec"))

    compileOnly(libs.jackson)
}
