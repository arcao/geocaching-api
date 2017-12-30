package com.arcao.geocaching.api.data;

import com.arcao.geocaching.api.data.type.GeocacheType;
import com.google.auto.value.AutoValue;

import java.io.Serializable;

/**
 * Created by Krzysztof.Nowacki on 2015.11.13.
 */
@AutoValue
public abstract class GeocacheStatus implements Serializable {
    private static final long serialVersionUID = -7261546635689151998L;

    public abstract boolean archived();

    public abstract boolean available();

    public abstract String cacheCode();

    public abstract String cacheName();

    public abstract GeocacheType cacheType();

    public abstract boolean premium();

    public abstract int trackableCount();

    public static Builder builder() {
        return new AutoValue_GeocacheStatus.Builder()
                .archived(false)
                .available(false)
                .premium(false)
                .trackableCount(0);
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder archived(boolean archived);

        public abstract Builder available(boolean available);

        public abstract Builder cacheCode(String cacheCode);

        public abstract Builder cacheName(String cacheName);

        public abstract Builder cacheType(GeocacheType cacheType);

        public abstract Builder premium(boolean premium);

        public abstract Builder trackableCount(int trackableCount);

        public abstract GeocacheStatus build();
    }
}
