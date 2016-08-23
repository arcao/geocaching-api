package com.arcao.geocaching.api.data;

import com.arcao.geocaching.api.data.userprofile.FavoritePointStats;
import com.arcao.geocaching.api.data.userprofile.GeocacheFindStats;
import com.arcao.geocaching.api.data.userprofile.GlobalStats;
import com.arcao.geocaching.api.data.userprofile.PublicProfile;
import com.arcao.geocaching.api.data.userprofile.TrackableStats;
import com.arcao.geocaching.api.util.DebugUtils;
import com.google.auto.value.AutoValue;

import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.List;

@AutoValue
public abstract class UserProfile implements Serializable {
    private static final long serialVersionUID = 872593420072537813L;

    @Nullable public abstract FavoritePointStats favoritePointsStats();

    @Nullable public abstract GeocacheFindStats geocacheFindStats();

    @Nullable public abstract PublicProfile publicProfile();

    @Nullable public abstract List<Souvenir> souvenirs();

    public abstract GlobalStats globalStats();

    @Nullable public abstract TrackableStats trackableStats();

    public abstract User user();

    @Override
    public String toString() {
        return DebugUtils.toString(this);
    }

    public static Builder builder() {
        return new AutoValue_UserProfile.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder favoritePointsStats(FavoritePointStats favoritePointsStats);

        public abstract Builder geocacheFindStats(GeocacheFindStats geocacheFindStats);

        public abstract Builder publicProfile(PublicProfile publicProfile);

        public abstract Builder souvenirs(List<Souvenir> souvenirs);

        public abstract Builder globalStats(GlobalStats globalStats);

        public abstract Builder trackableStats(TrackableStats trackableStats);

        public abstract Builder user(User user);

        public abstract UserProfile build();
    }
}
