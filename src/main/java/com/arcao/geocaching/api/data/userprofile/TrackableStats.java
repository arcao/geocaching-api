package com.arcao.geocaching.api.data.userprofile;

import com.google.auto.value.AutoValue;

import java.io.Serializable;
import java.util.List;

@AutoValue
public abstract class TrackableStats implements Serializable {
    private static final long serialVersionUID = 8539963736459413400L;

    public abstract int findCount();

    public abstract List<TrackableTypeCount> findTypes();

    public abstract int ownedCount();

    public abstract List<TrackableTypeCount> ownedTypes();

    public static Builder builder() {
        return new AutoValue_TrackableStats.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder findCount(int findCount);

        public abstract Builder findTypes(List<TrackableTypeCount> findTypes);

        public abstract Builder ownedCount(int ownedCount);

        public abstract Builder ownedTypes(List<TrackableTypeCount> ownedTypes);

        public abstract TrackableStats build();
    }
}
