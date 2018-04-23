package com.arcao.geocaching.api.data;

import com.google.auto.value.AutoValue;

import java.io.Serializable;

/**
 * Container for current state of Geocache retrieve limit information.
 *
 * @author arcao
 */
@AutoValue
public abstract class GeocacheLimits implements Serializable {
    private static final long serialVersionUID = 907830786611718961L;

    /**
     * Returns the count of remain geocaches in a limit.
     *
     * @return remain caches
     */
    public abstract int geocacheLeft();

    /**
     * Returns the count of geocaches which was used from a limit.
     *
     * @return count of caches which was used from a limit
     */
    public abstract int currentGeocacheCount();

    /**
     * Returns the count of geocaches in a limit.
     *
     * @return count of caches in a limit
     */
    public abstract int maxGeocacheCount();

    public static Builder builder() {
        return new AutoValue_GeocacheLimits.Builder()
                .geocacheLeft(0)
                .currentGeocacheCount(0)
                .maxGeocacheCount(0);
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder geocacheLeft(int geocacheLeft);
        public abstract Builder currentGeocacheCount(int currentGeocacheCount);
        public abstract Builder maxGeocacheCount(int maxGeocacheCount);

        public abstract GeocacheLimits build();
    }
}
