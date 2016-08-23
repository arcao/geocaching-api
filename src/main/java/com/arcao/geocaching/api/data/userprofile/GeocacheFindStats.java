package com.arcao.geocaching.api.data.userprofile;

import com.google.auto.value.AutoValue;

import java.io.Serializable;

@AutoValue
public abstract class GeocacheFindStats implements Serializable {
    private static final long serialVersionUID = -3901690388175569693L;

    public static Builder builder() {
        return new AutoValue_GeocacheFindStats.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract GeocacheFindStats build();
    }
}
