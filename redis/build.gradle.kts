dependencies {
    compileOnly(project(":codec"))
    compileOnly(project(":common"))

    compileOnly(libs.redis)
}
