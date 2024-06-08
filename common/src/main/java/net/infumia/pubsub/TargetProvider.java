package net.infumia.pubsub;

import java.util.Arrays;
import java.util.Collection;

/**
 * The interface for providing {@link Target}s.
 */
public interface TargetProvider {
    /**
     * Creates a non-dynamic target provider.
     *
     * @param targets the targets to create.
     * @return a newly created target provider.
     */
    static TargetProvider of(final Collection<Target> targets) {
        return () -> targets;
    }

    /**
     * Creates a non-dynamic target provider.
     *
     * @param targets the targets to create.
     * @return a newly created target provider.
     */
    static TargetProvider of(final Target... targets) {
        return of(Arrays.asList(targets));
    }

    /**
     * Provides the message targets.
     *
     * @return the message targets. Can be {@code null}.
     */
    Collection<Target> provide();
}
