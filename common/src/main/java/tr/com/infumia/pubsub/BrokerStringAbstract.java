package tr.com.infumia.pubsub;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Abstract class for a message broker that operates with strings and supports sending, receiving, and responding to messages.
 */
public abstract class BrokerStringAbstract implements Broker {
    private final UUID brokerId = UUID.randomUUID();
    private final HandlerRegistry handlerRegistry = new HandlerRegistry();
    protected final Target responderTarget = Internal.responderTarget(this.brokerId);
    private final Cache<UUID, AwaitingResponder<?>> awaitingResponders = Caffeine.newBuilder()
        .expireAfterWrite(Internal.RESPOND_TIMEOUT)
        .build();

    private final CodecProvider codecProvider;

    /**
     * Ctor.
     *
     * @param codecProvider the CodecProvider used for encoding and decoding messages. Cannot be {@code null}.
     */
    protected BrokerStringAbstract(final CodecProvider codecProvider) {
        this.codecProvider = codecProvider;
    }

    @Override
    public final void initialize() {
        this.connect();
    }

    @Override
    public final void send(final Object message, final Collection<Target> targets) {
        this.sendEnvelope(
            Internal.newEnvelope(this.codecProvider, this.brokerId, message),
            this.channelsForMessage(message, targets)
        );
    }

    @Override
    public final AutoCloseable listen(final Handler<?> handler) {
        return this.respond(new HandlerToResponder<>(handler));
    }

    @Override
    public final <R> CompletableFuture<R> request(final Object message, final Class<R> responseType, final Duration timeout, final Collection<Target> targets) {
        final Envelope envelope = Internal.newEnvelope(this.codecProvider, this.brokerId, message);
        final AwaitingResponder<R> responder = new AwaitingResponder<>(responseType);
        this.awaitingResponders.put(envelope.messageId, responder);
        this.sendEnvelope(envelope, this.channelsForMessage(message, targets));
        return responder.await(timeout);
    }

    @Override
    public final AutoCloseable respond(final Responder<?, ?> responder) {
        return this.handlerRegistry.register(this.messageTypeId(responder.type()), responder);
    }

    @Override
    public void close() {
        this.handlerRegistry.close();
    }

    /**
     * Calls registered handlers based on the provided channel and encoded data.
     *
     * @param channel     the channel from which the message was received. Cannot be {@code null}.
     * @param encodedData the encoded data representing the message. Cannot be {@code null}.
     */
    protected final void callHandlers(final String channel, final String encodedData) {
        final Envelope envelope = this.codecProvider.provide(Envelope.class).decode(Hex.decode(encodedData));
        if (envelope.respondsTo != null) {
            this.handleResponderEnvelope(envelope);
            return;
        }
        final Collection<Responder<?, ?>> responders = this.handlerRegistry.get(this.messageTypeIdForChannel(channel));
        if (responders == null) {
            return;
        }
        for (final Responder<?, ?> responder : responders) {
            final Envelope response = this.handleEnvelope(envelope, responder);
            if (response != null) {
                final Collection<String> previousChannel = this.channelsForMessage(
                    responder,
                    Collections.singleton(Internal.responderTarget(envelope.brokerId))
                );
                this.sendEnvelope(response, previousChannel);
            }
        }
    }

    /**
     * Establishes a connection to the message broker.
     */
    protected abstract void connect();

    /**
     * Determines the channels to which a message should be sent based on the message type ID and target collection.
     *
     * @param messageTypeId the ID of the message type. Cannot be {@code null}.
     * @param targets       the collection of targets to which the message should be sent. Cannot be {@code null}.
     * @return a collection of channel names. Cannot be {@code null}.
     */
    protected abstract Collection<String> channelsFor(String messageTypeId, Collection<Target> targets);

    /**
     * Retrieves the message type ID for the specified channel.
     *
     * @param channel the name of the channel. Cannot be {@code null}.
     * @return the message type ID associated with the channel. Cannot be {@code null}.
     */
    protected abstract String messageTypeIdForChannel(String channel);

    /**
     * Sends serialized data to the specified channels.
     *
     * @param channels      the collection of channels to which the data should be sent. Cannot be {@code null}.
     * @param serializedData the serialized data to send. Cannot be {@code null}.
     */
    protected abstract void sendData(Collection<String> channels, String serializedData);

    private void sendEnvelope(final Envelope envelope, final Collection<String> channels) {
        this.sendData(channels, Hex.encode(this.codecProvider.provide(Envelope.class).encode(envelope)));
    }

    private void handleResponderEnvelope(final Envelope envelope) {
        final AwaitingResponder<?> responder = this.awaitingResponders.getIfPresent(envelope.respondsTo);
        if (responder != null) {
            this.handleResponderEnvelope(envelope, responder);
        }
    }

    private <T> void handleResponderEnvelope(final Envelope envelope, final AwaitingResponder<T> responder) {
        responder.complete(this.codecProvider.provide(responder.responseType).decode(envelope.messagePayload));
    }

    private <T, Y> Envelope handleEnvelope(
        final Envelope envelope,
        final Responder<T, Y> responder
    ) {
        final T decoded = this.codecProvider.provide(responder.type()).decode(envelope.messagePayload);
        final Y response = responder.handle(decoded);
        if (response == null) {
            return null;
        } else {
            return Internal.newRespondingEnvelope(this.codecProvider, this.brokerId, envelope, response);
        }
    }

    private Collection<String> channelsForMessage(final Object message, final Collection<Target> targets) {
        return this.channelsFor(this.messageTypeId(message.getClass()), targets);
    }

    private String messageTypeId(final Class<?> messageType) {
        return messageType.toString();
    }
}
