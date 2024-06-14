package net.infumia.pubsub;

import java.time.Duration;
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
     * @param message the message to send. Cannot be {@code null}
     * @param targets the targets to send the message to. Cannot be {@code null}
     */
    void send(Object message, Collection<Target> targets);

    /**
     * Sends a message to the specified targets.
     * <p>
     * Sends to {@link Target#global()} if {@code targets} is not specified.
     *
     * @param message the message to send. Cannot be {@code null}
     * @param targets the targets to send the message to. Can be empty, cannot be {@code null}
     */
    void send(Object message, Target... targets);

    /**
     * Sends a message globally.
     * <p>
     * Sends to {@link Target#global()} if {@code targets} is not specified.
     *
     * @param message the message to send. Cannot be {@code null}
     */
    void send(Object message);

    /**
     * Listens for messages using a handler.
     *
     * @param handler the handler to process incoming messages. Cannot be {@code null}
     * @param <T>     the type of the message to listen for.
     * @return an {@link AutoCloseable} to stop listening. Cannot be {@code null}
     */
    <T> AutoCloseable listen(Handler<T> handler);

    /**
     * Listens for messages of a specific type using a consumer.
     *
     * @param type    the class object representing the type {@link T}. Cannot be {@code null}
     * @param handler the consumer to process incoming messages. Cannot be {@code null}
     * @param <T>     the type of the message to listen for.
     * @return an {@link AutoCloseable} to stop listening. Cannot be {@code null}
     */
    <T> AutoCloseable listen(Class<T> type, Consumer<T> handler);

    /**
     * Sends a request to targets and awaits a response within a timeout.
     *
     * @param message      the request message. Cannot be {@code null}
     * @param responseType the class object representing the response type {@link R}. Cannot be {@code null}
     * @param timeout      the duration to wait for a response. Cannot be {@code null}
     * @param targets      the targets to send the request to. Cannot be {@code null}
     * @param <R>          the type of the response.
     * @return a {@link CompletableFuture} representing the response. Cannot be {@code null}
     */
    <R> CompletableFuture<R> request(Object message, Class<R> responseType, Duration timeout,
                                     Collection<Target> targets);

    /**
     * Sends a request and awaits a response within a timeout.
     *
     * @param message      the request message. Cannot be {@code null}
     * @param responseType the class object representing the response type {@link R}. Cannot be {@code null}
     * @param timeout      the duration to wait for a response. Cannot be {@code null}
     * @param targets      the targets to send the request to. Can be empty, cannot be {@code null}
     * @param <R>          the type of the response.
     * @return a {@link CompletableFuture} representing the response. Cannot be {@code null}
     */
    <R> CompletableFuture<R> request(Object message, Class<R> responseType, Duration timeout, Target... targets);

    /**
     * Sends a request to targets and awaits a response using the default timeout.
     *
     * @param message      the request message. Cannot be {@code null}
     * @param responseType the class object representing the response type {@link R}. Cannot be {@code null}
     * @param targets      the targets to send the request to. Cannot be {@code null}
     * @param <R>          the type of the response.
     * @return a {@link CompletableFuture} representing the response. Cannot be {@code null}
     */
    <R> CompletableFuture<R> request(Object message, Class<R> responseType, Collection<Target> targets);

    /**
     * Sends a request and awaits a response using the default timeout.
     *
     * @param message      the request message. Cannot be {@code null}
     * @param responseType the class object representing the response type {@link R}. Cannot be {@code null}
     * @param targets      the targets to send the request to. Can be empty, cannot be {@code null}
     * @param <R>          the type of the response.
     * @return a {@link CompletableFuture} representing the response. Cannot be {@code null}
     */
    <R> CompletableFuture<R> request(Object message, Class<R> responseType, Target... targets);

    /**
     * Registers a responder to handle requests of a specific type.
     *
     * @param responder the responder to handle requests. Cannot be {@code null}
     * @param <T>       the type of the request message.
     * @param <Y>       the type of the response.
     * @return an {@link AutoCloseable} to stop responding. Cannot be {@code null}
     */
    <T, Y> AutoCloseable respond(Responder<T, Y> responder);

    /**
     * Registers a function to respond to requests of a specific type.
     *
     * @param type      the class object representing the type {@link T}. Cannot be {@code null}
     * @param responder the function to handle requests and produce responses. Cannot be {@code null}
     * @param <T>       the type of the request message.
     * @param <Y>       the type of the response.
     * @return an {@link AutoCloseable} to stop responding. Cannot be {@code null}
     */
    <T, Y> AutoCloseable respond(Class<T> type, Function<T, Y> responder);

    @Override
    void close();
}
