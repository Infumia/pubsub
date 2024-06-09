# pubsub
[![Maven Central Version](https://img.shields.io/maven-central/v/net.infumia/pubsub)](https://central.sonatype.com/artifact/net.infumia/pubsub)
## How to Use (Developers)
### Gradle
```groovy
repositories {
    mavenCentral()
}

dependencies {
    // Base module
    implementation "net.infumia:pubsub:VERSION"
    // Required, https://mvnrepository.com/artifact/com.github.ben-manes.caffeine/caffeine/
    implementation "com.github.ben-manes.caffeine:caffeine:2.9.3" // for java-8+
    implementation "com.github.ben-manes.caffeine:caffeine:3.1.8" // for java-11+

    // Pub/Sub using Redis (Optional)
    implementation "net.infumia:pubsub-redis:VERSION"
    // Required, https://mvnrepository.com/artifact/io.lettuce/lettuce-core/
    implementation "io.lettuce:lettuce-core:6.3.2.RELEASE"

    // A simple codec using Jackson (Optional)
    implementation "net.infumia:pubsub-jackson:VERSION"
    // Required, https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind
    implementation "com.fasterxml.jackson.core:jackson-databind:2.17.1"

    // Kotlin extensions (Optional)
    implementation "net.infumia:pubsub-kotlin:VERSION"

    // Kotlin protobuf serializer (Optional)
    implementation "net.infumia:pubsub-kotlin-protobuf:VERSION"
    // Required, https://mvnrepository.com/artifact/org.jetbrains.kotlin/kotlin-reflect/
    implementation "org.jetbrains.kotlin:kotlin-reflect:2.0.0"
    // Required, https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-serialization-core/
    implementation "org.jetbrains.kotlinx:kotlinx-serialization-core:1.7.0"
    // Required, https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-serialization-protobuf/
    implementation "org.jetbrains.kotlinx:kotlinx-serialization-protobuf:1.7.0"
}
```
### Code
```kotlin
fun main() {
    val mapper = ObjectMapper()
    val redisCredentials = RedisCredentials.just(null, "local")
    val redisAuth = StaticCredentialsProvider(redisCredentials)
    val redisUri = RedisURI.builder()
        .withHost("localhost")
        .withPort(6379)
        .withAuthentication(redisAuth)
        .build()
    val client = RedisClient.create(redisUri)
    val codecProviderJackson = CodecProviderJackson { mapper }
    val codecProvider = CodecProviderCached(codecProviderJackson)
    val broker = BrokerRedisNoTargetProvider(codecProvider) { client }
    broker.initialize()
    broker.listen<Test> {
        println(it.test)
    }
    while (true) {
        broker.send(Test("Hello World"))
        Thread.sleep(1000L)
    }
    // ...
    broker.close()
}

class Test(var test: String = "")
```
