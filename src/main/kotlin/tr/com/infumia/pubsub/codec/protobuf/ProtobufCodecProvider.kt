package tr.com.infumia.pubsub.codec.protobuf

import kotlin.reflect.full.createType
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.protobuf.ProtoBuf
import kotlinx.serialization.serializer
import tr.com.infumia.pubsub.codec.Codec
import tr.com.infumia.pubsub.codec.CodecProvider

@OptIn(ExperimentalSerializationApi::class)
class ProtobufCodecProvider : CodecProvider {

    override fun <T : Any> provide(type: Class<T>): Codec<T> =
        ProtobufCodec(ProtoBuf.serializersModule.serializer(type.kotlin.createType()) as KSerializer<T>)
}
