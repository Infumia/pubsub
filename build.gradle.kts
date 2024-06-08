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
            languageVersion = JavaLanguageVersion.of(8)
        }
    }

    tasks {
        compileJava {
            options.compilerArgs.add("-Xlint:-processing")
            options.compilerArgs.add("-Xlint:-options")
        }
    }
}

indraSonatype {
    useAlternateSonatypeOSSHost("s01")
}
