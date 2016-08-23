package com.arcao.geocaching.api.data;


import com.arcao.geocaching.api.data.coordinates.Coordinates;
import com.arcao.geocaching.api.data.type.MemberType;
import com.google.auto.value.AutoValue;

import org.jetbrains.annotations.Nullable;

import java.io.Serializable;

@AutoValue
public abstract class User implements Serializable {
    private static final long serialVersionUID = 1808891631464643511L;

    public abstract long id();

    public abstract String publicGuid();

    public abstract String userName();

    public abstract String avatarUrl();

    @Nullable public abstract Coordinates homeCoordinates();

    public abstract boolean admin();

    @Nullable public abstract MemberType memberType();

    public abstract int findCount();

    public abstract int hideCount();

    public abstract int galleryImageCount();

    public static Builder builder() {
        return new AutoValue_User.Builder().galleryImageCount(0);
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder id(long id);

        public abstract Builder publicGuid(String publicGuid);

        public abstract Builder userName(String userName);

        public abstract Builder avatarUrl(String avatarUrl);

        public abstract Builder homeCoordinates(Coordinates homeCoordinates);

        public abstract Builder admin(boolean admin);

        public abstract Builder memberType(MemberType memberType);

        public abstract Builder findCount(int findCount);

        public abstract Builder hideCount(int hideCount);

        public abstract Builder galleryImageCount(int galleryImageCount);

        public abstract User build();
    }
}
