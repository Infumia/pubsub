package net.infumia.pubsub;

import java.util.HashMap;
import java.util.Map;

/**
 * A cached implementation of the {@link CodecProvider} interface.
 */
public final class CodecProviderCached implements CodecProvider {
    private final Map<Class<?>, Codec<?>> cache = new HashMap<>();
    private final CodecProvider delegate;

    /**
     * Ctor.
     *
     * @param delegate the delegate {@link CodecProvider} used to provide codecs.
     */
    public CodecProviderCached(final CodecProvider delegate) {
        this.delegate = delegate;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Codec<T> provide(final Class<T> type) {
        return (Codec<T>) this.cache.computeIfAbsent(type, __ ->
            this.delegate.provide(type));
    }
}
