# pubsub
![Sonatype Nexus (Releases)](https://img.shields.io/nexus/r/tr.com.infumia/pubsub?label=maven-central&server=https%3A%2F%2Foss.sonatype.org%2F)
![Sonatype Nexus (Snapshots)](https://img.shields.io/nexus/s/tr.com.infumia/pubsub?label=maven-central&server=https%3A%2F%2Foss.sonatype.org)
## How to Use (Developers)
### Code
```groovy
repositories {
  maven("https://jitpack.io/")
}

dependencies {
    // Base module
    implementation "tr.com.infumia:pubsub:VERSION"

    // Pub/Sub using Redis (Optional)
    implementation "tr.com.infumia:pubsub-redis:VERSION"
    // https://mvnrepository.com/artifact/io.lettuce/lettuce-core/
    implementation "io.lettuce:lettuce-core:6.3.2.RELEASE" // required

    // Kotlin extensions (Optional)
    implementation "tr.com.infumia:pubsub-kotlin:VERSION"

    // Kotlin protobuf serializer (Optional)
    implementation "tr.com.infumia:pubsub-kotlin-protobuf:VERSION"
    // https://mvnrepository.com/artifact/org.jetbrains.kotlin/kotlin-reflect/
    implementation "org.jetbrains.kotlin:kotlin-reflect:2.0.0" // required
    // https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-serialization-core/
    implementation "org.jetbrains.kotlinx:kotlinx-serialization-core:1.7.0" // required
    // https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-serialization-protobuf/
    implementation "org.jetbrains.kotlinx:kotlinx-serialization-protobuf:1.7.0" // required
}
```
```java
void pubsub() {}
```
