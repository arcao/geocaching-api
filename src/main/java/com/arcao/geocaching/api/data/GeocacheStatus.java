package com.arcao.geocaching.api.data;

import com.arcao.geocaching.api.data.type.GeocacheType;
import com.arcao.geocaching.api.util.DebugUtils;

import java.io.Serializable;

/**
 * Created by Krzysztof.Nowacki on 2015.11.13.
 */
public class GeocacheStatus implements Serializable {

    private final boolean archived;
    private final boolean available;
    private final String cacheCode;
    private final String cacheName;
    private final GeocacheType cacheType;
    private final boolean premium;
    private final int trackableCount;

    private GeocacheStatus(boolean archived, boolean available, String cacheCode, String cacheName, GeocacheType cacheType, boolean premium, int trackableCount) {
        this.archived = archived;
        this.available = available;
        this.cacheCode = cacheCode;
        this.cacheName = cacheName;
        this.cacheType = cacheType;
        this.premium = premium;
        this.trackableCount = trackableCount;
    }

    public boolean isArchived() {
        return archived;
    }

    public boolean isAvailable() {
        return available;
    }

    public String getCacheCode() {
        return cacheCode;
    }

    public String getCacheName() {
        return cacheName;
    }

    public GeocacheType getCacheType() {
        return cacheType;
    }

    public boolean isPremium() {
        return premium;
    }

    public int getTrackableCount() {
        return trackableCount;
    }

    @Override
    public String toString() {
        return DebugUtils.toString(this);
    }

    public static class Builder {

        private boolean archived;
        private boolean available;
        private String cacheCode;
        private String cacheName;
        private GeocacheType cacheType;
        private boolean premium;
        private int trackableCount;

        private Builder() {
        }

        public static Builder geocacheStatus() {
            return new Builder();
        }

        public Builder withArchived(boolean archived) {
            this.archived = archived;
            return this;
        }

        public Builder withAvailable(boolean available) {
            this.available = available;
            return this;
        }

        public Builder withCacheCode(String cacheCode) {
            this.cacheCode = cacheCode;
            return this;
        }

        public Builder withCacheName(String cacheName) {
            this.cacheName = cacheName;
            return this;
        }

        public Builder withCacheType(GeocacheType cacheType) {
            this.cacheType = cacheType;
            return this;
        }

        public Builder withPremium(boolean premium) {
            this.premium = premium;
            return this;
        }

        public Builder withTrackableCount(int trackableCount) {
            this.trackableCount = trackableCount;
            return this;
        }

        public GeocacheStatus build() {
            return new GeocacheStatus(
                    archived,
                    available,
                    cacheCode,
                    cacheName,
                    cacheType,
                    premium,
                    trackableCount);
        }

    }

}
