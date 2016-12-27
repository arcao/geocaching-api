package com.arcao.geocaching.api.data.userprofile;

import com.arcao.geocaching.api.data.type.GeocacheType;
import com.google.auto.value.AutoValue;

import java.io.Serializable;

@AutoValue
public abstract class GeocacheTypeCount implements Serializable {
    private static final long serialVersionUID = -5154501054241628739L;

    public abstract GeocacheType type();

    public abstract int count();

    public static GeocacheTypeCount create(GeocacheType type, int count) {
        return builder()
                .type(type)
                .count(count)
                .build();
    }

    public static Builder builder() {
        return new AutoValue_GeocacheTypeCount.Builder();
    }


    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder type(GeocacheType type);

        public abstract Builder count(int count);

        public abstract GeocacheTypeCount build();
    }
}
