package tr.com.infumia.pubsub;

/**
 * The interface for handling Pub/Sub messages of type {@link T}.
 *
 * @param <T> the type of the message to be handled.
 */
public interface Handler<T> {
    /**
     * Retrieves the class type of the message being handled.
     *
     * @return the {@link Class} object representing the type {@link T}.
     */
    Class<T> type();

    /**
     * Handles a message of type {@link T} received from Pub/Sub.
     *
     * @param t the message to handle.
     */
    void handle(T t);
}
