plugins {
    java
    `maven-publish`
    alias(libs.plugins.nexus)
}

repositories.mavenCentral()

subprojects {
    apply<JavaPlugin>()

    repositories.mavenCentral()

    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(8)
        }
    }

    tasks {
        compileJava { options.encoding = Charsets.UTF_8.name() }

        javadoc {
            options.encoding = Charsets.UTF_8.name()
            (options as StandardJavadocDocletOptions).tags("todo")
        }

        val javadocJar by creating(Jar::class) {
            dependsOn("javadoc")
            archiveClassifier.set("javadoc")
            from(javadoc)
        }

        val sourcesJar by creating(Jar::class) {
            dependsOn("classes")
            archiveClassifier.set("sources")
            from(sourceSets["main"].allSource)
        }

        build {
            dependsOn(jar)
            dependsOn(sourcesJar)
            dependsOn(javadocJar)
        }
    }
}

nexusPublishing.repositories.sonatype {
    val baseUrl = "https://s01.oss.sonatype.org/"
    nexusUrl = uri("${baseUrl}service/local/")
    snapshotRepositoryUrl = uri("${baseUrl}content/repositories/snapshots/")
}
