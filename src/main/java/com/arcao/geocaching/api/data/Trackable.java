package com.arcao.geocaching.api.data;

import com.google.auto.value.AutoValue;

import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@AutoValue
public abstract class Trackable implements Serializable {
    private static final long serialVersionUID = 5984147222015866863L;
    private static final String TRACKABLE_URL = "http://www.geocaching.com/track/details.aspx?tracker=%s";

    public abstract long id();

    @Nullable public abstract String name();

    @Nullable public abstract String goal();

    @Nullable public abstract String description();

    public abstract String trackableTypeName();

    public abstract String trackableTypeImage();

    @Nullable public abstract User owner();

    @Nullable public abstract String currentCacheCode();

    @Nullable public abstract User currentOwner();

    @Nullable public abstract String trackingNumber();

    public abstract Date created();

    public abstract boolean allowedToBeCollected();

    public abstract boolean inCollection();

    public abstract boolean archived();

    @Nullable public abstract List<TrackableLog> trackableLogs();

    @Nullable public abstract List<ImageData> images();

    public String trackableUrl() {
        return String.format(TRACKABLE_URL, trackingNumber());
    }

    public static Builder builder() {
        return new AutoValue_Trackable.Builder()
                .allowedToBeCollected(false)
                .inCollection(false)
                .archived(false);
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder id(long id);

        public abstract Builder name(String name);

        public abstract Builder goal(String goal);

        public abstract Builder description(String description);

        public abstract Builder trackableTypeName(String trackableTypeName);

        public abstract Builder trackableTypeImage(String trackableTypeImage);

        public abstract Builder owner(User owner);

        public abstract Builder currentCacheCode(String currentCacheCode);

        public abstract Builder currentOwner(User currentOwner);

        public abstract Builder trackingNumber(String trackingNumber);

        public abstract Builder created(Date created);

        public abstract Builder allowedToBeCollected(boolean allowedToBeCollected);

        public abstract Builder inCollection(boolean inCollection);

        public abstract Builder archived(boolean archived);

        public abstract Builder trackableLogs(List<TrackableLog> trackableLogs);

        public abstract Builder images(List<ImageData> images);

        public abstract Trackable build();
    }
}
