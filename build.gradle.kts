import com.vanniktech.maven.publish.MavenPublishBaseExtension
import com.vanniktech.maven.publish.MavenPublishPlugin
import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.dokka.gradle.DokkaPlugin

plugins {
    java
    alias(libs.plugins.nexus) apply false
    alias(libs.plugins.kotlin) apply false
    alias(libs.plugins.dokka) apply false
}

repositories.mavenCentral()

subprojects {
    apply<JavaPlugin>()
    apply<MavenPublishPlugin>()
    if (project.name.contains("kotlin")) {
        apply<DokkaPlugin>()
        apply(plugin = "org.jetbrains.kotlin.jvm")
    }

    repositories.mavenCentral()

    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(8)
        }
    }

    tasks {
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
    }

    val moduleName = project.findProperty("artifact-id") as String?
    val projectName = "pubsub${if (moduleName == null) "" else "-$moduleName"}"
    val signRequired = project.hasProperty("sign-required")

    extensions.configure<MavenPublishBaseExtension> {
        coordinates(project.group.toString(), projectName, project.version.toString())
        publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL, true)
        if (signRequired) {
            signAllPublications()
        }

        pom {
            name.set(projectName)
            description.set("Simplified pubsub library for Redis and various databases.")
            url.set("https://github.com/Infumia/pubsub")
            licenses {
                license {
                    name.set("MIT License")
                    url.set("https://mit-license.org/license.txt")
                }
            }
            developers {
                developer {
                    id.set("portlek")
                    name.set("Hasan Demirtaş")
                    email.set("utsukushihito@outlook.com")
                }
            }
            scm {
                connection.set("scm:git:git://github.com/infumia/pubsub.git")
                developerConnection.set("scm:git:ssh://github.com/infumia/pubsub.git")
                url.set("https://github.com/infumia/pubsub/")
            }
        }
    }
}
