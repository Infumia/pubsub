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
    // Kotlin extensions (Optional)
    implementation "tr.com.infumia:pubsub-kotlin:VERSION"
    // Kotlin protobuf serializer (Optional)
    implementation "tr.com.infumia:pubsub-kotlin-protobuf:VERSION"
}
```
```java
void pubsub() {}
```
