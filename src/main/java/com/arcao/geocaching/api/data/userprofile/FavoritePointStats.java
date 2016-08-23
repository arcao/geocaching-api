package com.arcao.geocaching.api.data.userprofile;

import com.google.auto.value.AutoValue;

import java.io.Serializable;

@AutoValue
public abstract class FavoritePointStats implements Serializable {
    private static final long serialVersionUID = -5759425542897341813L;

    public static Builder builder() {
        return new AutoValue_FavoritePointStats.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract FavoritePointStats build();
    }
}
