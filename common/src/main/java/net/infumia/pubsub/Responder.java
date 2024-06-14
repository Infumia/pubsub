package net.infumia.pubsub;

import java.util.function.Function;

/**
 * The interface for handling Pub/Sub messages of type {@code T} and providing a response of type {@code Y}.
 *
 * @param <T> the type of the message to be handled.
 * @param <Y> the type of the response.
 */
public interface Responder<T, Y> extends Function<T, Y> {

    /**
     * Retrieves the class type of the message being handled.
     *
     * @return the {@link Class} object representing the type {@link T}.
     */
    Class<T> type();
}
