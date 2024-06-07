package tr.com.infumia.pubsub;

import tr.com.infumia.pubsub.codec.CodecProvider;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

final class Internal {
    private static final ScheduledExecutorService DELAYER = Executors.newScheduledThreadPool(1);
    static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(10L);
    static final Duration RESPOND_TIMEOUT = Duration.ofMinutes(1L);

    static <T> CompletableFuture<T> delay(
        final CompletableFuture<T> future,
        final Duration timeout
    ) {
        if (future.isDone()) {
            return future;
        }
        final ScheduledFuture<?> delay = Internal.DELAYER.schedule(
            () -> future.completeExceptionally(new TimeoutException()),
            timeout.toMillis(),
            TimeUnit.MILLISECONDS
        );
        return future.whenComplete((__, ___) -> {
            if (!delay.isDone()) {
                delay.cancel(false);
            }
        });
    }

    @SuppressWarnings("unchecked")
    static Envelope newEnvelope(
        final CodecProvider codecProvider,
        final UUID brokerId,
        final Object payload
    ) {
        return new Envelope(
            brokerId,
            UUID.randomUUID(),
            null,
            codecProvider.provide((Class<Object>) payload.getClass()).encode(payload)
        );
    }

    @SuppressWarnings("unchecked")
    static Envelope newRespondingEnvelope(
        final CodecProvider codecProvider,
        final UUID brokerId,
        final Envelope request,
        final Object response
    ) {
        return new Envelope(
            brokerId,
            UUID.randomUUID(),
            request.messageId,
            codecProvider.provide((Class<Object>) response.getClass()).encode(response)
        );
    }

    static Target responderTarget(final UUID brokerId) {
        return Target.of("_BROKER", brokerId.toString());
    }

    private Internal() {
        throw new IllegalStateException("Utility class");
    }
}
