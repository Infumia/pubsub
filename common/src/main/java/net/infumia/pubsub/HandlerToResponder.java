package net.infumia.pubsub;

final class HandlerToResponder<T> implements Responder<T, Object> {
    private final Handler<T> delegate;

    HandlerToResponder(final Handler<T> delegate) {
        this.delegate = delegate;
    }

    @Override
    public Class<T> type() {
        return this.delegate.type();
    }

    @Override
    public Object handle(final T t) {
        this.delegate.handle(t);
        return null;
    }
}
