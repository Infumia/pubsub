plugins { id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0" }

rootProject.name = "pubsub"

include(
    "codec",
    "common",
    "redis",
    "jackson",
)

registerInnerModule("kotlin", "extensions")

registerInnerModule("kotlin", "coroutines")

registerInnerModule("kotlin", "protobuf")

private fun registerInnerModule(vararg paths: String) {
    val moduleName = paths.joinToString("-")
    include(moduleName)
    project(":$moduleName").projectDir = file(paths.joinToString("/"))
}
