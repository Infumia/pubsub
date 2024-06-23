package net.infumia.pubsub;

import java.util.concurrent.Executor;

/**
 * A concrete implementation of {@link BrokerRedis} that does not provide a target provider.
 */
public final class BrokerRedisNoTargetProvider extends BrokerRedis {

    /**
     * Ctor.
     *
     * @param codecProvider  the CodecProvider used for encoding and decoding messages.
     * @param clientProvider the RedisClientProvider used for obtaining Redis client connections.
     * @param executor       the Executor used for calling handlers whenever redis receives a messages. Can be null.
     */
    public BrokerRedisNoTargetProvider(
        final CodecProvider codecProvider,
        final RedisClientProvider clientProvider,
        final Executor executor
    ) {
        super(codecProvider, clientProvider, executor);
    }

    /**
     * Ctor.
     *
     * @param codecProvider  the CodecProvider used for encoding and decoding messages.
     * @param clientProvider the RedisClientProvider used for obtaining Redis client connections.
     */
    public BrokerRedisNoTargetProvider(
        final CodecProvider codecProvider,
        final RedisClientProvider clientProvider
    ) {
        super(codecProvider, clientProvider);
    }

    @Override
    protected TargetProvider targetProvider() {
        return null;
    }
}
