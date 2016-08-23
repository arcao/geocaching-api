package com.arcao.geocaching.api.data;

import com.arcao.geocaching.api.data.coordinates.Coordinates;
import com.google.auto.value.AutoValue;

import java.io.Serializable;
import java.util.Date;

@AutoValue
public abstract class TrackableTravel implements Serializable {
    private static final long serialVersionUID = 61007459728740881L;

    public abstract long cacheId();

    public abstract Date dateLogged();

    public abstract Coordinates coordinates();

    public static Builder builder() {
        return new AutoValue_TrackableTravel.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder cacheId(long cacheId);

        public abstract Builder dateLogged(Date dateLogged);

        public abstract Builder coordinates(Coordinates coordinates);

        public abstract TrackableTravel build();
    }
}
