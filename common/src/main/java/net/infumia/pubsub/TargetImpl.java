package net.infumia.pubsub;

final class TargetImpl implements Target {
    static final Target GLOBAL = of("", "");

    private final String type;
    private final String identifier;

    TargetImpl(final String type, final String identifier) {
        this.type = type;
        this.identifier = identifier;
    }

    @Override
    public String type() {
        return this.type;
    }

    @Override
    public String identifier() {
        return this.identifier;
    }
}
