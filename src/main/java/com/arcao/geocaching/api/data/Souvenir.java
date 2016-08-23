package com.arcao.geocaching.api.data;

import com.google.auto.value.AutoValue;

import java.io.Serializable;

@AutoValue
public abstract class Souvenir implements Serializable {
    private static final long serialVersionUID = -1185578859898344877L;

    public static Builder builder() {
        return new AutoValue_Souvenir.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Souvenir build();
    }
}
