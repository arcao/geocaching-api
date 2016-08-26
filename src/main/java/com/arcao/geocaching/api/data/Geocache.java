package com.arcao.geocaching.api.data;

import com.arcao.geocaching.api.data.coordinates.Coordinates;
import com.arcao.geocaching.api.data.type.AttributeType;
import com.arcao.geocaching.api.data.type.ContainerType;
import com.arcao.geocaching.api.data.type.GeocacheType;
import com.google.auto.value.AutoValue;

import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;

@AutoValue
public abstract class Geocache implements Serializable {
    private static final long serialVersionUID = 7938069911500506011L;

    // ResultQuality.LITE
    public abstract boolean archived();

    public abstract boolean available();

    public abstract GeocacheType geocacheType();

    public abstract boolean favoritable();

    public abstract String code();

    public abstract ContainerType containerType();

    @Nullable public abstract Date lastUpdateDate();

    @Nullable public abstract Date lastVisitDate();

    public abstract float difficulty();

    public abstract int favoritePoints();

    @Nullable public abstract Date foundDate();

    @Nullable public abstract String personalNote();

    public abstract boolean favoritedByUser();

    public abstract boolean foundByUser();

    public abstract long id();

    public abstract int imageCount(); // only LITE and FULL

    public abstract boolean premium();

    public abstract boolean recommended();

    public abstract Coordinates coordinates();

    public abstract String name();

    public abstract User owner();

    public abstract String placedBy();

    @Nullable public abstract Date publishDate();

    public abstract float terrain();

    public abstract int trackableCount(); // only LITE and FULL

    public abstract Date placeDate();

    public abstract String url();

    public abstract String guid();


    // ResultQuality.SUMMARY
    @Nullable public abstract List<Waypoint> waypoints();

    @Nullable public abstract String hint();

    @Nullable public abstract String longDescription();

    public abstract boolean longDescriptionHtml();

    @Nullable public abstract String shortDescription();

    public abstract boolean shortDescriptionHtml();

    @Nullable public abstract List<Trackable> trackables();

    @Nullable public abstract List<UserWaypoint> userWaypoints();

    @Nullable public abstract List<ImageData> images();

    // ResultQuality.FULL
    @Nullable public abstract EnumSet<AttributeType> attributes();

    @Nullable public abstract String countryName();

    @Nullable public abstract Date createDate();

    @Nullable public abstract List<GeocacheLog> geocacheLogs();

    @Nullable public abstract String stateName();

    public static Builder builder() {
        return new AutoValue_Geocache.Builder().longDescriptionHtml(false).shortDescriptionHtml(false);
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder archived(boolean archived);

        public abstract Builder available(boolean available);

        public abstract Builder geocacheType(GeocacheType geocacheType);

        public abstract Builder favoritable(boolean favoritable);

        public abstract Builder code(String code);

        public abstract Builder containerType(ContainerType containerType);

        public abstract Builder lastUpdateDate(Date lastUpdateDate);

        public abstract Builder lastVisitDate(Date lastVisitDate);

        public abstract Builder difficulty(float difficulty);

        public abstract Builder favoritePoints(int favoritePoints);

        public abstract Builder foundDate(Date foundDate);

        public abstract Builder personalNote(String personalNote);

        public abstract Builder favoritedByUser(boolean favoritedByUser);

        public abstract Builder foundByUser(boolean foundByUser);

        public abstract Builder id(long id);

        public abstract Builder imageCount(int imageCount);

        public abstract Builder premium(boolean premium);

        public abstract Builder recommended(boolean recommended);

        public abstract Builder coordinates(Coordinates coordinates);

        public abstract Builder name(String name);

        public abstract Builder owner(User owner);

        public abstract Builder placedBy(String placedBy);

        public abstract Builder publishDate(Date publishDate);

        public abstract Builder terrain(float terrain);

        public abstract Builder trackableCount(int trackableCount);

        public abstract Builder placeDate(Date placeDate);

        public abstract Builder url(String url);

        public abstract Builder guid(String guid);

        public abstract Builder waypoints(List<Waypoint> waypoints);

        public abstract Builder hint(String hint);

        public abstract Builder longDescription(String longDescription);

        public abstract Builder longDescriptionHtml(boolean longDescriptionHtml);

        public abstract Builder shortDescription(String shortDescription);

        public abstract Builder shortDescriptionHtml(boolean shortDescriptionHtml);

        public abstract Builder trackables(List<Trackable> trackables);

        public abstract Builder userWaypoints(List<UserWaypoint> userWaypoints);

        public abstract Builder images(List<ImageData> images);

        public abstract Builder attributes(EnumSet<AttributeType> attributes);

        public abstract Builder countryName(String countryName);

        public abstract Builder createDate(Date createDate);

        public abstract Builder geocacheLogs(List<GeocacheLog> geocacheLogs);

        public abstract Builder stateName(String stateName);

        public abstract Geocache build();
    }
}

