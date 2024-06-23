package net.infumia.pubsub;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * A {@link CodecProvider} implementation that uses Jackson for JSON serialization and deserialization.
 */
public final class CodecProviderJackson implements CodecProvider {

    private final JacksonProvider provider;

    /**
     * Ctor.
     *
     * @param provider the {@link JacksonProvider} used to provide {@link ObjectMapper} instances.
     */
    public CodecProviderJackson(final JacksonProvider provider) {
        this.provider = provider;
    }

    @Override
    public <T> Codec<T> provide(final Class<T> type) {
        return new CodecJackson<>(this.provider.provide(), type);
    }
}
