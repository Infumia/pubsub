import java.nio.file.Path

plugins {
    java
    id("io.github.goooler.shadow") version "8.1.7"
}

group = "tr.com.infumia"
version = "1.0.0-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

tasks {
    jar {
        archiveVersion = ""
    }

    shadowJar {
        archiveClassifier = ""
        archiveVersion = ""

        mergeServiceFiles()

        manifest {
            attributes(
                "Main-Class" to "tr.com.infumia.Main"
            )
        }
    }

    build {
        dependsOn(shadowJar)
    }
}

repositories {
    mavenCentral()
    maven("https://jitpack.io/")
}

dependencies {
    implementation("com.github.infumia:pubsub:1.0.0")
}
