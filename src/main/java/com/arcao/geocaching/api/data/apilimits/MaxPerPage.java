package com.arcao.geocaching.api.data.apilimits;

import com.google.auto.value.AutoValue;

import java.io.Serializable;

/**
 * Container class for a max per page limit information.
 *
 * @author arcao
 */
@AutoValue
public abstract class MaxPerPage implements Serializable {
    private static final long serialVersionUID = -5594435197387844111L;

    /**
     * Returns a max per page value for retrieving Geocaches.
     *
     * @return max per page value for Geocaches
     */
    public abstract int geocaches();

    /**
     * Returns a max per page value for retrieving Geocache logs.
     *
     * @return max per page value for Geocache logs
     */
    public abstract int geocacheLogs();

    /**
     * Returns a max per page value for retrieving Trackables.
     *
     * @return max per page value for Trackables
     */
    public abstract int trackables();

    /**
     * Returns a max per page value for retrieving Trackable logs.
     *
     * @return max per page value for Trackable logs
     */
    public abstract int trackableLogs();

    /**
     * Returns a max per page value for retrieving Cache notes.
     *
     * @return max per page value for Cache notes
     */
    public abstract int cacheNotes();

    /**
     * Returns a max per page value for retrieving Gallery images.
     *
     * @return max per page value for Gallery images
     */
    public abstract int galleryImages();

    public static Builder builder() {
        return new AutoValue_MaxPerPage.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder geocaches(int geocaches);

        public abstract Builder geocacheLogs(int geocacheLogs);

        public abstract Builder trackables(int trackables);

        public abstract Builder trackableLogs(int trackableLogs);

        public abstract Builder cacheNotes(int cacheNotes);

        public abstract Builder galleryImages(int galleryImages);

        public abstract MaxPerPage build();
    }
}
