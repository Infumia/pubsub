plugins {
    id("tr.com.infumia.pubsub.build.publishing")
}

dependencies {
    compileOnly(project(":common"))

    compileOnly(libs.redis)
}
