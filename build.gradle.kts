plugins {
  java
  `java-library`
  `maven-publish`
  alias(libs.plugins.kotlin)
  alias(libs.plugins.kotlin.serialization)
}

repositories {
  mavenCentral()
}

dependencies {
  compileOnly(libs.caffeine)
  compileOnly(libs.redis)
  compileOnly(libs.kotlin.reflect)
  compileOnly(libs.kotlinx.serialization.core)
  compileOnly(libs.kotlinx.serialization.protobuf)
}

java {
  toolchain {
    languageVersion.set(JavaLanguageVersion.of(8))
  }
}

tasks {
  compileJava {
    options.encoding = Charsets.UTF_8.name()
  }

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
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
    from(sourceSets["main"].allSource)
  }

  processResources {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
  }
}

publishing {
  publications {
    create<MavenPublication>("mavenJava") {
      groupId = project.group.toString()
      artifactId = "pubsub"
      version = project.version.toString()
      from(components["java"])
      artifact(tasks["sourcesJar"])
      artifact(tasks["javadocJar"])
      pom {
        name.set("pubsub")
        description.set("Simplified pubsub library for Redis and various databases.")
        url.set("https://infumia.com.tr/")
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
          url.set("https://github.com/infumia/pubsub")
        }
      }
    }
  }
}
