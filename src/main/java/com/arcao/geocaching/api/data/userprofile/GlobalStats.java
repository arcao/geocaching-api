package com.arcao.geocaching.api.data.userprofile;

import com.google.auto.value.AutoValue;

import java.io.Serializable;

@AutoValue
public abstract class GlobalStats implements Serializable {
    private static final long serialVersionUID = 7066712324435905861L;

    public abstract long accountsLogged();

    public abstract long activeCaches();

    public abstract long activeCountries();

    public abstract long newLogs();

    public static Builder builder() {
        return new AutoValue_GlobalStats.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder accountsLogged(long accountsLogged);

        public abstract Builder activeCaches(long activeCaches);

        public abstract Builder activeCountries(long activeCountries);

        public abstract Builder newLogs(long newLogs);

        public abstract GlobalStats build();
    }
}
