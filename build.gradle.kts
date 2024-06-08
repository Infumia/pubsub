plugins {
  java
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
