package com.arcao.geocaching.api.data.userprofile;

import com.arcao.geocaching.api.data.Trackable;
import com.google.auto.value.AutoValue;

import java.io.Serializable;
import java.util.List;

@AutoValue
public abstract class TrackableStats implements Serializable {
    private static final long serialVersionUID = 8539963736459413400L;

    public abstract int trackableFindCount();
    public abstract List<Trackable> trackableFindTypes();
    public abstract int trackableOwnedCount();
    public abstract List<Trackable> trackableOwnedTypes();

    public static Builder builder() {
        return new AutoValue_TrackableStats.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder trackableFindCount(int trackableFindCount);

        public abstract Builder trackableFindTypes(List<Trackable> trackableFindTypes);

        public abstract Builder trackableOwnedCount(int trackableOwnedCount);

        public abstract Builder trackableOwnedTypes(List<Trackable> trackableOwnedTypes);

        public abstract TrackableStats build();
    }
}
