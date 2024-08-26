import net.infumia.gradle.applyPublish

applyPublish("redis")

dependencies {
    compileOnly(project(":codec"))
    compileOnly(project(":common"))

    compileOnly(libs.redis)
}
