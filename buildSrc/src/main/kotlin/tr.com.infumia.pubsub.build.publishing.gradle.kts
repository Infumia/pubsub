plugins {
    signing
    `maven-publish`
}

val projectName = project.property("artifact-id") as String
val signRequired = project.hasProperty("sign-required")

publishing {
    publications {
        val publication = create<MavenPublication>("maven") {
            groupId = project.group.toString()
            artifactId = projectName
            version = project.version.toString()

            from(components["java"])
            artifact(tasks["sourcesJar"])
            artifact(tasks["javadocJar"])

            pom {
                name.set(projectName)
                description.set("Simplified pubsub library for Redis and various databases.")
                url.set("https://github.com/Infumia/")
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

        signing {
            isRequired = signRequired
            if (isRequired) {
                useGpgCmd()
                sign(publication)
            }
        }
    }
}
