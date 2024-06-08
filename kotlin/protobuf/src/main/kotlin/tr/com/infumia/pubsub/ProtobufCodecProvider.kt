package tr.com.infumia.pubsub

import kotlin.reflect.full.createType
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.protobuf.ProtoBuf
import kotlinx.serialization.serializer

@Suppress("UNCHECKED_CAST")
@OptIn(ExperimentalSerializationApi::class)
class ProtobufCodecProvider : CodecProvider {
    override fun <T : Any> provide(type: Class<T>): Codec<T> =
        ProtobufCodec(ProtoBuf.serializersModule.serializer(type.kotlin.createType()) as KSerializer<T>)
}
