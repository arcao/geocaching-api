package com.arcao.geocaching.api.data;

import com.arcao.geocaching.api.data.userprofile.FavoritePointStats;
import com.arcao.geocaching.api.data.userprofile.GeocacheStats;
import com.arcao.geocaching.api.data.userprofile.GlobalStats;
import com.arcao.geocaching.api.data.userprofile.PublicProfile;
import com.arcao.geocaching.api.data.userprofile.TrackableStats;
import com.google.auto.value.AutoValue;

import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.List;

@AutoValue
public abstract class UserProfile implements Serializable {
    private static final long serialVersionUID = 872593420072537813L;

    @Nullable public abstract FavoritePointStats favoritePointsStats();

    @Nullable public abstract GeocacheStats geocacheStats();

    @Nullable public abstract PublicProfile publicProfile();

    @Nullable public abstract List<Souvenir> souvenirs();

    public abstract GlobalStats globalStats();

    @Nullable public abstract TrackableStats trackableStats();

    public abstract User user();

    public static Builder builder() {
        return new AutoValue_UserProfile.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder favoritePointsStats(FavoritePointStats favoritePointsStats);

        public abstract Builder publicProfile(PublicProfile publicProfile);

        public abstract Builder souvenirs(List<Souvenir> souvenirs);

        public abstract Builder globalStats(GlobalStats globalStats);

        public abstract Builder trackableStats(TrackableStats trackableStats);

        public abstract Builder user(User user);

        public abstract Builder geocacheStats(GeocacheStats geocacheStats);

        public abstract UserProfile build();
    }
}
