package com.arcao.geocaching.api.data;

import com.arcao.geocaching.api.data.coordinates.Coordinates;
import com.arcao.geocaching.api.data.type.GeocacheLogType;
import com.google.auto.value.AutoValue;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * GeocacheLog class keep all information cache log.
 *
 * @author arcao
 */
@AutoValue
public abstract class GeocacheLog implements Serializable {
    private static final long serialVersionUID = 9088433857246687793L;

    /**
     * Get a unique identificator of the cache log.
     *
     * @return unique identifiactor
     */
    public abstract long id();

    /**
     * Get a gccode of the cache.
     *
     * @return gccode of the cache
     */
    public abstract String cacheCode();

    /**
     * Get a date when the cache log was created (logged) on Geocaching site.
     *
     * @return date
     */
    public abstract Date created();

    /**
     * Get a date when the cache was visited (found).
     *
     * @return date
     */
    public abstract Date visited();

    /**
     * Get a type of log.
     *
     * @return type of log
     */
    public abstract GeocacheLogType logType();

    /**
     * Get an author of log.
     *
     * @return author of log
     */
    public abstract User author();

    /**
     * Get a text of log.
     *
     * @return text of log
     */
    public abstract String text();

    /**
     * Get the images attached to log.
     *
     * @return images
     */
    public abstract List<ImageData> images();

    /**
     * Get an updated coordinates.
     *
     * @return updated coordinates
     */
    public abstract Coordinates updatedCoordinates();

    /**
     * Return true if log is approved.
     *
     * @return log approved?
     */
    public abstract boolean approved();

    /**
     * Return true if log is archived.
     *
     * @return log archived?
     */
    public abstract boolean archived();

    /**
     * Return true if log can not be deleted.
     *
     * @return can not be deleted?
     */
    public abstract boolean undeletable();

    public static Builder builder() {
        return new AutoValue_GeocacheLog.Builder()
                .approved(false)
                .archived(false)
                .undeletable(false);
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder id(long id);

        public abstract Builder cacheCode(String cacheCode);

        public abstract Builder created(Date created);

        public abstract Builder visited(Date visited);

        public abstract Builder logType(GeocacheLogType logType);

        public abstract Builder author(User author);

        public abstract Builder text(String text);

        public abstract Builder images(List<ImageData> images);

        public abstract Builder updatedCoordinates(Coordinates updatedCoordinates);

        public abstract Builder approved(boolean approved);

        public abstract Builder archived(boolean archived);

        public abstract Builder undeletable(boolean undeletable);

        public abstract GeocacheLog build();
    }
}
