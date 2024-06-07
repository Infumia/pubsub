package tr.com.infumia.pubsub;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

final class Envelope {
    final UUID brokerId;
    final UUID messageId;
    final UUID respondsTo;
    final byte[] messagePayload;

    Envelope(
        final UUID brokerId,
        final UUID messageId,
        final UUID respondsTo,
        final byte[] messagePayload
    ) {
        this.brokerId = brokerId;
        this.messageId = messageId;
        this.respondsTo = respondsTo;
        this.messagePayload = messagePayload;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final Envelope envelope = (Envelope) o;
        return Objects.equals(this.brokerId, envelope.brokerId) &&
               Objects.equals(this.messageId, envelope.messageId) &&
               Objects.equals(this.respondsTo, envelope.respondsTo) &&
               Objects.deepEquals(this.messagePayload, envelope.messagePayload);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.brokerId, this.messageId, this.respondsTo, Arrays.hashCode(this.messagePayload));
    }
}
