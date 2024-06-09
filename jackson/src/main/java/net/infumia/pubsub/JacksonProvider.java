package net.infumia.pubsub;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * A provider interface for supplying {@link ObjectMapper} instances.
 */
public interface JacksonProvider {
    /**
     * Provides an {@link ObjectMapper} instance.
     *
     * @return an {@link ObjectMapper} instance.
     */
    ObjectMapper provide();
}
