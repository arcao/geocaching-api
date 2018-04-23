package com.arcao.geocaching.api.data;

import com.arcao.geocaching.api.data.coordinates.Coordinates;
import com.arcao.geocaching.api.data.type.TrackableLogType;
import com.arcao.geocaching.api.util.GeocachingUtils;
import com.google.auto.value.AutoValue;

import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@AutoValue
public abstract class TrackableLog implements Serializable {
    private static final long serialVersionUID = -8616502691991228922L;

    public abstract int cacheId();

    public abstract String code();

    public abstract int id();

    public abstract List<ImageData> images();

    public abstract boolean archived();

    public abstract String guid();

    public abstract String text();

    public abstract TrackableLogType type();

    public abstract User loggedBy();

    public abstract Date created();

    public abstract Coordinates updatedCoordinates();

    public abstract String url();

    public abstract Date visited();

    @Nullable
    public String cacheCode() {
        if (cacheId() == 0) {
            return null;
        }
        return GeocachingUtils.cacheIdToCacheCode(cacheId());
    }

    public static Builder builder() {
        return new AutoValue_TrackableLog.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder cacheId(int cacheId);

        public abstract Builder code(String code);

        public abstract Builder id(int id);

        public abstract Builder images(List<ImageData> images);

        public abstract Builder archived(boolean archived);

        public abstract Builder guid(String guid);

        public abstract Builder text(String text);

        public abstract Builder type(TrackableLogType type);

        public abstract Builder loggedBy(User loggedBy);

        public abstract Builder created(Date created);

        public abstract Builder updatedCoordinates(Coordinates updatedCoordinates);

        public abstract Builder url(String url);

        public abstract Builder visited(Date visited);

        public abstract TrackableLog build();
    }
}
