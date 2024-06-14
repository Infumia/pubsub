package net.infumia.pubsub;

/**
 * The interface for encoding and decoding objects of type {@link T} to and from byte arrays.
 *
 * @param <T> type of the object to be encoded and decoded.
 */
public interface Codec<T> {
    /**
     * Encodes an object of type {@link T} into a byte array.
     *
     * @param t the object to encode.
     * @return a byte array representing the encoded object.
     */
    byte[] encode(T t);

    /**
     * Decodes a byte array into an object of type {@link T}.
     *
     * @param bytes the bytes array to decode.
     * @return the decoded object.
     */
    T decode(byte[] bytes);
}
