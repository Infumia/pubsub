import net.infumia.gradle.publish

publish("redis")

dependencies {
    compileOnly(project(":codec"))
    compileOnly(project(":common"))

    compileOnly(libs.redis)
}
