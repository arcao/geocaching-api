package com.arcao.geocaching.api.data.userprofile;

import com.google.auto.value.AutoValue;

import java.io.Serializable;

@AutoValue
public abstract class ProfilePhoto implements Serializable {
    private static final long serialVersionUID = 5557754921065357998L;

    public abstract String photoDescription();

    public abstract String photoFilename();

    public abstract String photoName();

    public abstract String photoUrl();

    public static Builder builder() {
        return new AutoValue_ProfilePhoto.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder photoDescription(String photoDescription);

        public abstract Builder photoFilename(String photoFilename);

        public abstract Builder photoName(String photoName);

        public abstract Builder photoUrl(String photoUrl);

        public abstract ProfilePhoto build();
    }
}
