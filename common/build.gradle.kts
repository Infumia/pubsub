import net.infumia.gradle.applyPublish

applyPublish()

dependencies {
    compileOnly(project(":codec"))

    compileOnly(libs.caffeine)
}
