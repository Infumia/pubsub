package tr.com.infumia.pubsub;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * The interface for a message broker that supports sending, receiving, and responding to messages.
 */
public interface Broker extends AutoCloseable {
    /**
     * Initializes the message broker.
     */
    void initialize();

    /**
     * Sends a message to the specified targets.
     * <p>
     * Sends to {@link Target#global()} if {@code targets} is not specified.
     *
     * @param message the message to send.
     * @param targets the targets to send the message to.
     */
    void send(Object message, Collection<Target> targets);

    /**
     * Sends a message to the specified targets.
     * <p>
     * Sends to {@link Target#global()} if {@code targets} is not specified.
     *
     * @param message the message to send.
     * @param targets the targets to send the message to.
     */
    default void send(final Object message, final Target... targets) {
        this.send(message, Arrays.asList(targets));
    }

    /**
     * Listens for messages using a handler.
     *
     * @param handler the handler to process incoming messages.
     * @return an {@link AutoCloseable} to stop listening.
     */
    AutoCloseable listen(Handler<?> handler);

    /**
     * Listens for messages of a specific type using a consumer.
     *
     * @param type    the class object representing the type {@link T}.
     * @param handler the consumer to process incoming messages.
     * @param <T>     the type of the message to listen for.
     * @return an {@link AutoCloseable} to stop listening.
     */
    default <T> AutoCloseable listen(final Class<T> type, final Consumer<T> handler) {
        return this.listen(new Handler<T>() {
            @Override
            public Class<T> type() {
                return type;
            }

            @Override
            public void handle(final T t) {
                handler.accept(t);
            }
        });
    }

    /**
     * Sends a request to targets and awaits a response within a timeout.
     *
     * @param message      the request message.
     * @param responseType the class object representing the response type {@link R}.
     * @param timeout      the duration to wait for a response.
     * @param targets      the targets to send the request to.
     * @param <R>          the type of the response.
     * @return a {@link CompletableFuture} representing the response
     */
    <R> CompletableFuture<R> request(Object message, Class<R> responseType, Duration timeout, Collection<Target> targets);

    /**
     * Sends a request and awaits a response within a timeout.
     *
     * @param message      the request message.
     * @param responseType the class object representing the response type {@link R}.
     * @param timeout      the duration to wait for a response.
     * @param targets      the targets to send the request to.
     * @param <R>          the type of the response.
     * @return a {@link CompletableFuture} representing the response.
     */
    default <R> CompletableFuture<R> request(final Object message, final Class<R> responseType, final Duration timeout, final Target... targets) {
        return this.request(message, responseType, timeout, Arrays.asList(targets));
    }

    /**
     * Sends a request and awaits a response using the default timeout.
     *
     * @param message      the request message.
     * @param responseType the class object representing the response type {@link R}.
     * @param targets      the targets to send the request to.
     * @param <R>          the type of the response.
     * @return a {@link CompletableFuture} representing the response.
     */
    default <R> CompletableFuture<R> request(final Object message, final Class<R> responseType, final Target... targets) {
        return this.request(message, responseType, Internal.REQUEST_TIMEOUT, targets);
    }

    /**
     * Sends a request to targets and awaits a response using the default timeout.
     *
     * @param message      the request message.
     * @param responseType the class object representing the response type {@link R}.
     * @param targets      the targets to send the request to.
     * @param <R>          the type of the response.
     * @return a {@link CompletableFuture} representing the response.
     */
    default <R> CompletableFuture<R> request(final Object message, final Class<R> responseType, final Collection<Target> targets) {
        return this.request(message, responseType, Internal.REQUEST_TIMEOUT, targets);
    }

    /**
     * Registers a responder to handle requests of a specific type.
     *
     * @param responder the responder to handle requests.
     * @return an {@link AutoCloseable} to stop responding.
     */
    AutoCloseable respond(Responder<?, ?> responder);

    /**
     * Registers a function to respond to requests of a specific type.
     *
     * @param type      the class object representing the type {@link T}.
     * @param responder the function to handle requests and produce responses.
     * @param <T>       the type of the request message.
     * @param <Y>       the type of the response.
     * @return an {@link AutoCloseable} to stop responding.
     */
    default <T, Y> AutoCloseable respond(final Class<T> type, final Function<T, Y> responder) {
        return this.respond(new Responder<T, Y>() {
            @Override
            public Class<T> type() {
                return type;
            }

            @Override
            public Y handle(final T t) {
                return responder.apply(t);
            }
        });
    }
}
