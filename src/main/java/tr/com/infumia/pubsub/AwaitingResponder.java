package tr.com.infumia.pubsub;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

final class AwaitingResponder<T> {
    private final CompletableFuture<T> future = new CompletableFuture<>();
    final Class<T> responseType;

    AwaitingResponder(final Class<T> responseType) {
        this.responseType = responseType;
    }

    void complete(final T response) {
        this.future.complete(response);
    }

    CompletableFuture<T> await(final Duration timeout) {
        return Internal.delay(this.future, timeout);
    }
}
