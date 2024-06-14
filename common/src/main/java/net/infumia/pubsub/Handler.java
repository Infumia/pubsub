package net.infumia.pubsub;

import java.util.function.Consumer;

/**
 * The interface for handling Pub/Sub messages of type {@link T}.
 *
 * @param <T> the type of the message to be handled.
 */
public interface Handler<T> extends Consumer<T> {
    /**
     * Retrieves the class type of the message being handled.
     *
     * @return the {@link Class} object representing the type {@link T}.
     */
    Class<T> type();
}
