package net.infumia.pubsub;

/**
 * The interface for providing {@link Codec} instances based on the class type.
 */
public interface CodecProvider {
    /**
     * Provides a {@link Codec} instance for the specified class type.
     *
     * @param type the class object representing the type {@code T}
     * @param <T>   the type of the object for which the codec is to be provided
     * @return a {@link Codec} for the specified class type
     */
    <T> Codec<T> provide(Class<T> type);
}
