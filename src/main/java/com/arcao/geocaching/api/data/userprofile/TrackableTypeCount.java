package com.arcao.geocaching.api.data.userprofile;

import com.google.auto.value.AutoValue;

import org.jetbrains.annotations.Nullable;

import java.io.Serializable;

@AutoValue
public abstract class TrackableTypeCount implements Serializable {
    public abstract long id();

    public abstract String name();

    @Nullable
    public abstract String iconUrl();

    public abstract int count();

    public static Builder builder() {
        return new AutoValue_TrackableTypeCount.Builder();
    }


    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder id(long id);

        public abstract Builder name(String name);

        public abstract Builder iconUrl(String iconUrl);

        public abstract Builder count(int count);

        public abstract TrackableTypeCount build();
    }
}
