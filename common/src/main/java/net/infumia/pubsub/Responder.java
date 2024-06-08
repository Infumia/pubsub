package net.infumia.pubsub;

/**
 * The interface for handling Pub/Sub messages of type {@code T} and providing a response of type {@code Y}.
 *
 * @param <T> the type of the message to be handled.
 * @param <Y> the type of the response.
 */
public interface Responder<T, Y> {

    /**
     * Retrieves the class type of the message being handled.
     *
     * @return the {@link Class} object representing the type {@link T}.
     */
    Class<T> type();

    /**
     * Handles a message of type {@link T} received from Pub/Sub and provides a response of type {@link Y}.
     *
     * @param t the message to handle.
     * @return the response of type {@link Y}.
     */
    Y handle(T t);
}
