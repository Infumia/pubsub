package net.infumia.pubsub;

import java.util.function.Supplier;

final class Lazy<T> implements Supplier<T> {

    static <T> Lazy<T> of(final Supplier<T> supplier) {
        return new Lazy<>(supplier);
    }

    private final Supplier<T> supplier;

    private volatile T value;

    private Lazy(final Supplier<T> supplier) {
        this.supplier = supplier;
    }

    @Override
    public T get() {
        T val = this.value;
        if (val == null) {
            synchronized (this) {
                val = this.value;
                if (val == null) {
                    val = this.supplier.get();
                    this.value = val;
                }
            }
        }
        return val;
    }
}
