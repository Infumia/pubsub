plugins {
    id("net.kyori.indra")
    id("net.kyori.indra.publishing")
}

val projectName = project.property("artifact-id") as String

indra {
    mitLicense()
    github("infumia", "pubsub")
    configurePublications {
        artifactId = projectName

        pom {
            name = projectName
            inceptionYear = "2024"
            description = "Simplified pubsub library for Redis and various databases."
            developers {
                developer {
                    name = "Hasan Demirtaş"
                    url = "https://github.com/portlek/"
                }
            }
            organization {
                name = "Infumia"
                url = "https://github.com/infumia/"
            }
        }
    }
    if (project.hasProperty("sign-required")) {
        signWithKeyFromPrefixedProperties("infumia")
    }
}
