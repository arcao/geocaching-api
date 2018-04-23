package com.arcao.geocaching.api.data.apilimits;

import com.google.auto.value.AutoValue;

import java.io.Serializable;

/**
 * Container class for a cache limit information.
 *
 * @author arcao
 */
@AutoValue
public abstract class CacheLimit implements Serializable {
    private static final long serialVersionUID = 5480568653613770776L;

    /**
     * Returns a cache limit.
     *
     * @return limit
     */
    public abstract long limit();

    /**
     * Returns a period for this limit in the minutes.
     *
     * @return period in minutes
     */
    public abstract long period();

    public static Builder builder() {
        return new AutoValue_CacheLimit.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder limit(long limit);

        public abstract Builder period(long period);

        public abstract CacheLimit build();
    }
}
