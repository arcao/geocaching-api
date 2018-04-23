package com.arcao.geocaching.api.data;

import com.arcao.geocaching.api.data.coordinates.Coordinates;
import com.arcao.geocaching.api.util.GeocachingUtils;
import com.google.auto.value.AutoValue;

import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.Date;

@AutoValue
public abstract class UserWaypoint implements Serializable {
    private static final long serialVersionUID = 2635449057331423781L;

    @Nullable public abstract String cacheCode();

    @Nullable
    public abstract String description();

    public abstract long id();

    public abstract Coordinates coordinates();

    public abstract Date date();

    public abstract int userId();

    public abstract boolean correctedCoordinate();

    public String userWaypointCode(int index) {
        String cacheCode = cacheCode();
        if (cacheCode == null) {
            return null;
        }

        long base = GeocachingUtils.base31Decode("U1");
        String value = GeocachingUtils.base31Encode(base + index);

        return value.substring(value.length() - 2, value.length()) + cacheCode.substring(2);
    }

    public static Builder builder() {
        return new AutoValue_UserWaypoint.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder cacheCode(String cacheCode);

        public abstract Builder description(String description);

        public abstract Builder id(long id);

        public abstract Builder coordinates(Coordinates coordinates);

        public abstract Builder date(Date date);

        public abstract Builder userId(int userId);

        public abstract Builder correctedCoordinate(boolean correctedCoordinate);

        public abstract UserWaypoint build();
    }
}
