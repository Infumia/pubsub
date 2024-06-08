plugins {
    id("net.infumia.pubsub.build.publishing")
}

dependencies {
    compileOnly(project(":common"))

    compileOnly(libs.redis)
}
