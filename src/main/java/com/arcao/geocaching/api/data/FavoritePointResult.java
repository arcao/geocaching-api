package com.arcao.geocaching.api.data;

import com.google.auto.value.AutoValue;

import java.io.Serializable;

@AutoValue
public abstract class FavoritePointResult implements Serializable {
    private static final long serialVersionUID = -8463568668075962169L;

    public abstract int cacheFavoritePoints();

    public abstract int usersFavoritePoints();

    public static Builder builder() {
        return new AutoValue_FavoritePointResult.Builder()
                .cacheFavoritePoints(0).usersFavoritePoints(0);
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder cacheFavoritePoints(int cacheFavoritePoints);

        public abstract Builder usersFavoritePoints(int usersFavoritePoints);

        public abstract FavoritePointResult build();
    }
}
