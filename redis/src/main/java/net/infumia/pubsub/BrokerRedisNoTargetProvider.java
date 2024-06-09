package net.infumia.pubsub;

/**
 * A concrete implementation of {@link BrokerRedis} that does not provide a target provider.
 */
public final class BrokerRedisNoTargetProvider extends BrokerRedis {
    /**
     * Ctor.
     *
     * @param codecProvider  the CodecProvider used for encoding and decoding messages.
     * @param clientProvider the RedisClientProvider used for obtaining Redis client connections.
     */
    public BrokerRedisNoTargetProvider(final CodecProvider codecProvider, final RedisClientProvider clientProvider) {
        super(codecProvider, clientProvider);
    }

    @Override
    protected TargetProvider targetProvider() {
        return null;
    }
}
