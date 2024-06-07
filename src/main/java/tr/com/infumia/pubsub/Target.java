package tr.com.infumia.pubsub;

/**
 * The interface represents message targets.
 */
public interface Target {
    /**
     * Creates a target from {@code type} and {@code identifier}.
     *
     * @param type       the type of the target.
     * @param identifier the identifier of the target.
     * @return a newly created message target.
     */
    static Target of(final String type, final String identifier) {
        return new TargetImpl(type, identifier);
    }

    /**
     * Creates a global target for the message that will be published to all subscribers.
     *
     * @return a newly created global message target.
     */
    static Target global() {
        return TargetImpl.GLOBAL;
    }

    /**
     * Returns type of the target.
     *
     * @return type of the target.
     */
    String type();

    /**
     * Returns identifier of the target.
     *
     * @return identifier of the target.
     */
    String identifier();
}
