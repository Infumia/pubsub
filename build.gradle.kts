plugins {
  java
  alias(libs.plugins.indra.publishing.sonatype)
}

repositories.mavenCentral()

subprojects {
  apply<JavaPlugin>()

  repositories.mavenCentral()

  java {
    toolchain {
      languageVersion.set(JavaLanguageVersion.of(8))
    }
  }
}
