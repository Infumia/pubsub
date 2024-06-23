package net.infumia.pubsub

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.protobuf.ProtoBuf

@OptIn(ExperimentalSerializationApi::class)
internal class ProtobufCodec<T : Any>(private val serializer: KSerializer<T>) : Codec<T> {
    override fun encode(message: T): ByteArray = ProtoBuf.encodeToByteArray(serializer, message)

    override fun decode(message: ByteArray): T = ProtoBuf.decodeFromByteArray(serializer, message)
}
