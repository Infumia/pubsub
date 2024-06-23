package net.infumia.pubsub;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

final class CodecJackson<T> implements Codec<T> {

    private final ObjectMapper mapper;
    private final Class<T> type;

    CodecJackson(final ObjectMapper mapper, final Class<T> type) {
        this.mapper = mapper;
        this.type = type;
    }

    @Override
    public byte[] encode(final T t) {
        try {
            return this.mapper.writerFor(this.type).writeValueAsBytes(t);
        } catch (final JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize " + this.type, e);
        }
    }

    @Override
    public T decode(final byte[] bytes) {
        try {
            return this.mapper.readerFor(this.type).readValue(bytes);
        } catch (final IOException e) {
            throw new RuntimeException("Failed to deserialize " + this.type, e);
        }
    }
}
