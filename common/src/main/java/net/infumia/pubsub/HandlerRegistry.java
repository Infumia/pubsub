package net.infumia.pubsub;

import java.util.Collection;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

final class HandlerRegistry {

    private final ReentrantLock lock = new ReentrantLock();
    private final MultiMap<String, Responder<?, ?>> handlers = new MultiMap<>();

    void close() {
        this.withLock(this.handlers::clear);
    }

    AutoCloseable register(final String messageTypeId, final Responder<?, ?> handler) {
        this.withLock(() -> this.handlers.put(messageTypeId, handler));
        return () -> this.unregister(messageTypeId, handler);
    }

    void unregister(final String messageTypeId, final Responder<?, ?> handler) {
        this.withLock(() -> this.handlers.remove(messageTypeId, handler));
    }

    Collection<Responder<?, ?>> get(final String messageTypeId) {
        return this.withLock(() -> {
                final Collection<Responder<?, ?>> handlers = this.handlers.get(messageTypeId);
                if (handlers == null || handlers.isEmpty()) {
                    return null;
                }
                return handlers;
            });
    }

    private <T> T withLock(final Supplier<T> task) {
        this.lock.lock();
        try {
            return task.get();
        } finally {
            this.lock.unlock();
        }
    }

    private void withLock(final Runnable task) {
        this.lock.lock();
        try {
            task.run();
        } finally {
            this.lock.unlock();
        }
    }
}
