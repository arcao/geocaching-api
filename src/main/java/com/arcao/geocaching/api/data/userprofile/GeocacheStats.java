package com.arcao.geocaching.api.data.userprofile;

import com.google.auto.value.AutoValue;

import java.io.Serializable;
import java.util.List;

@AutoValue
public abstract class GeocacheStats implements Serializable {
    private static final long serialVersionUID = -3901690388175569693L;

    public abstract int findCount();

    public abstract List<GeocacheTypeCount> findTypes();

    public abstract int hideCount();

    public abstract List<GeocacheTypeCount> hideTypes();

    public static Builder builder() {
        return new AutoValue_GeocacheStats.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder findCount(int findCount);

        public abstract Builder findTypes(List<GeocacheTypeCount> findTypes);

        public abstract Builder hideCount(int hideCount);

        public abstract Builder hideTypes(List<GeocacheTypeCount> hideTypes);

        public abstract GeocacheStats build();
    }
}
