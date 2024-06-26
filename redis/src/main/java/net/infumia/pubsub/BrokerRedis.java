package net.infumia.pubsub;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.pubsub.RedisPubSubAdapter;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

/**
 * Abstract class for a Redis-based message broker.
 */
public abstract class BrokerRedis extends BrokerStringAbstract {

    private final Lazy<Collection<String>> channelPrefixes;
    private final RedisClientProvider clientProvider;
    private final Executor executor;

    private StatefulRedisConnection<String, String> publishConnection;
    private StatefulRedisPubSubConnection<String, String> subscribeConnection;

    /**
     * Ctor.
     *
     * @param codecProvider  the CodecProvider used for encoding and decoding messages. Cannot be null.
     * @param clientProvider the RedisClientProvider used for getting Redis client connections. Cannot be null.
     * @param executor       the Executor used for calling handlers whenever redis receives a messages. Can be null.
     */
    public BrokerRedis(
        final CodecProvider codecProvider,
        final RedisClientProvider clientProvider,
        final Executor executor
    ) {
        super(codecProvider);
        this.clientProvider = clientProvider;
        this.executor = executor;
        this.channelPrefixes = Lazy.of(() -> {
            final ArrayList<String> channels = new ArrayList<>();
            channels.addAll(Internal.channelPrefixFor(Collections.emptySet()));
            channels.addAll(Internal.channelPrefixFor(Collections.singleton(this.responderTarget)));
            final TargetProvider targetProvider = this.targetProvider();
            if (targetProvider != null) {
                final Collection<Target> targets = targetProvider.provide();
                if (targets != null) {
                    channels.addAll(Internal.channelPrefixFor(targets));
                }
            }
            return channels;
        });
    }

    /**
     * Ctor.
     *
     * @param codecProvider  the CodecProvider used for encoding and decoding messages. Cannot be null.
     * @param clientProvider the RedisClientProvider used for getting Redis client connections. Cannot be null.
     */
    public BrokerRedis(
        final CodecProvider codecProvider,
        final RedisClientProvider clientProvider
    ) {
        this(codecProvider, clientProvider, null);
    }

    @Override
    protected void connect() {
        final Executor executor = BrokerRedis.this.executor;
        final RedisClient client = this.clientProvider.provide();
        this.publishConnection = client.connect();
        this.subscribeConnection = client.connectPubSub();

        this.subscribeConnection.addListener(
                new RedisPubSubAdapter<String, String>() {
                    @Override
                    public void message(
                        final String pattern,
                        final String channel,
                        final String message
                    ) {
                        if (executor == null) {
                            BrokerRedis.this.callHandlers(channel, message);
                        } else {
                            executor.execute(() -> BrokerRedis.this.callHandlers(channel, message));
                        }
                    }
                }
            );
        final String[] channels =
            this.channelPrefixes.get().stream().map(s -> s + "*").toArray(String[]::new);
        this.subscribeConnection.sync().psubscribe(channels);
    }

    @Override
    protected Collection<String> channelsFor(
        final String messageTypeId,
        final Collection<Target> targets
    ) {
        return Internal.channelPrefixFor(targets)
            .stream()
            .map(s -> s + messageTypeId)
            .collect(Collectors.toList());
    }

    @Override
    protected String messageTypeIdForChannel(final String channel) {
        final String prefix =
            this.channelPrefixes.get()
                .stream()
                .filter(channel::startsWith)
                .findFirst()
                .orElseThrow(
                    () -> new IllegalStateException("No channel prefix found for " + channel)
                );
        return channel.substring(prefix.length());
    }

    @Override
    protected void sendData(final Collection<String> channels, final String serializedData) {
        for (final String channel : channels) {
            this.publishConnection.sync().publish(channel, serializedData);
        }
    }

    @Override
    public void close() {
        super.close();
        this.publishConnection.close();
        this.subscribeConnection.close();
    }

    /**
     * Provides the target provider used to determine target channels.
     *
     * @return the target provider instance. Can be {@code null}.
     */
    protected abstract TargetProvider targetProvider();

    private static final class Internal {

        private static final String CHANNEL_PREFIX = "Messaging:";
        private static final String GLOBAL_PREFIX = "Global:";
        private static final String TARGET_PREFIX = "Target:";

        private static Collection<String> channelPrefixFor(final Collection<Target> targets) {
            if (targets.isEmpty()) {
                return Collections.singletonList(Internal.CHANNEL_PREFIX + Internal.GLOBAL_PREFIX);
            }
            return targets
                .stream()
                .map(
                    target ->
                        Internal.CHANNEL_PREFIX +
                        Internal.TARGET_PREFIX +
                        target.type() +
                        ":" +
                        target.identifier()
                )
                .collect(Collectors.toList());
        }
    }
}
