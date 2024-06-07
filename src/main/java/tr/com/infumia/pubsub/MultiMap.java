package tr.com.infumia.pubsub;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.function.Supplier;

/**
 * A simple implementation of a multimap, which allows multiple values to be associated with a single key.
 *
 * @param <K> the type of keys maintained by this map.
 * @param <V> the type of mapped values.
 */
final class MultiMap<K, V> {
    private final Map<K, Collection<V>> map = new HashMap<>();
    private final Supplier<Collection<V>> valueFactory;

    /**
     * Ctor.
     *
     * @param valueFactory a Supplier of the collection type to be used for storing values.
     */
    public MultiMap(final Supplier<Collection<V>> valueFactory) {
        this.valueFactory = valueFactory;
    }

    /**
     * Ctor.
     */
    public MultiMap() {
        this(HashSet::new);
    }

    /**
     * Adds a value to the list of values associated with the specified key.
     *
     * @param key   the key with which the specified value is to be associated.
     * @param value the value to be associated with the specified key.
     */
    public void put(final K key, final V value) {
        this.map.computeIfAbsent(key, k -> this.valueFactory.get()).add(value);
    }

    /**
     * Returns the set of values to which the specified key is mapped, or {@code null} if this map contains no mapping for the key.
     *
     * @param key the key whose associated values are to be returned.
     * @return the list of values to which the specified key is mapped, or {@code null} if this map contains no mapping for the key.
     */
    public Collection<V> get(final K key) {
        return this.map.get(key);
    }

    /**
     * Removes a specific value from the list of values associated with the specified key.
     *
     * @param key   the key whose associated value is to be removed.
     * @param value the value to be removed from the list of values associated with the specified key.
     * @return {@code true} if the value was successfully removed, {@code false} otherwise.
     */
    public boolean remove(final K key, final V value) {
        final Collection<V> values = this.map.get(key);
        if (values != null) {
            final boolean removed = values.remove(value);
            if (values.isEmpty()) {
                this.map.remove(key);
            }
            return removed;
        }
        return false;
    }

    /**
     * Removes all values associated with the specified key.
     *
     * @param key the key whose mappings are to be removed from the map.
     * @return the previous list of values associated with the specified key, or {@code null} if there was no mapping for the key.
     */
    public Collection<V> removeAll(final K key) {
        return this.map.remove(key);
    }

    /**
     * Returns {@code true} if this map contains a mapping for the specified key.
     *
     * @param key the key whose presence in this map is to be tested.
     * @return {@code true} if this map contains a mapping for the specified key, {@code false} otherwise.
     */
    public boolean containsKey(final K key) {
        return this.map.containsKey(key);
    }

    /**
     * Returns {@code true} if this map maps one or more keys to the specified value.
     *
     * @param value the value whose presence in this map is to be tested.
     * @return {@code true} if this map maps one or more keys to the specified value, {@code false} otherwise.
     */
    public boolean containsValue(final V value) {
        return this.map.values().stream().anyMatch(list -> list.contains(value));
    }

    /**
     * Returns the number of key-value mappings in this map.
     *
     * @return the number of key-value mappings in this map.
     */
    public int size() {
        return this.map.size();
    }

    /**
     * Returns {@code true} if this map contains no key-value mappings.
     *
     * @return {@code true} if this map contains no key-value mappings.
     */
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    /**
     * Removes all the mappings from this map. The map will be empty after this call returns.
     */
    public void clear() {
        this.map.clear();
    }

    /**
     * Returns a string representation of the map.
     *
     * @return a string representation of the map.
     */
    @Override
    public String toString() {
        return this.map.toString();
    }
}