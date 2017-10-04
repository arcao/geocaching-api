package com.arcao.geocaching.api.data.userprofile;

import com.google.auto.value.AutoValue;

import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.Date;

@AutoValue
public abstract class PublicProfile implements Serializable {
    private static final long serialVersionUID = 7624712344301138677L;

    public abstract String forumTitle();

    public abstract Date lastVisit();

    public abstract String location();

    public abstract Date memberSince();

    public abstract String occupation();

    @Nullable
    public abstract ProfilePhoto profilePhoto();

    public abstract String profileText();

    public static Builder builder() {
        return new AutoValue_PublicProfile.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder forumTitle(String forumTitle);

        public abstract Builder lastVisit(Date lastVisit);

        public abstract Builder location(String location);

        public abstract Builder memberSince(Date memberSince);

        public abstract Builder occupation(String occupation);

        public abstract Builder profilePhoto(@Nullable ProfilePhoto profilePhoto);

        public abstract Builder profileText(String profileText);

        public abstract PublicProfile build();
    }
}
