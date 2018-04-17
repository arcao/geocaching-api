package com.arcao.geocaching.api.data.userprofile;

import com.google.auto.value.AutoValue;

import org.jetbrains.annotations.Nullable;

import java.io.Serializable;

@AutoValue
public abstract class ProfilePhoto implements Serializable {
    private static final long serialVersionUID = 5557754921065357998L;

    @Nullable
    public abstract String photoDescription();

    @Nullable
    public abstract String photoFilename();

    @Nullable
    public abstract String photoName();

    @Nullable
    public abstract String photoUrl();

    public static Builder builder() {
        return new AutoValue_ProfilePhoto.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder photoDescription(@Nullable String photoDescription);

        public abstract Builder photoFilename(@Nullable String photoFilename);

        public abstract Builder photoName(@Nullable String photoName);

        public abstract Builder photoUrl(@Nullable String photoUrl);

        public abstract ProfilePhoto build();
    }
}
