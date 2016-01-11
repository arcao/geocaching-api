package com.arcao.geocaching.api.data.apilimits;

import com.arcao.geocaching.api.util.DebugUtils;

import java.io.Serializable;

/**
 * Container class for a max per page limit information
 * @author arcao
 */
public class MaxPerPage implements Serializable {
  private static final long serialVersionUID = -5594435197387844111L;

  private final int geocaches;
  private final int geocacheLogs;
  private final int trackables;
  private final int trackableLogs;
  private final int cacheNotes;
  private final int galleryImages;

  public MaxPerPage(int geocaches, int geocacheLogs, int trackables, int trackableLogs, int cacheNotes, int galleryImages) {
    this.geocaches = geocaches;
    this.geocacheLogs = geocacheLogs;
    this.trackables = trackables;
    this.trackableLogs = trackableLogs;
    this.cacheNotes = cacheNotes;
    this.galleryImages = galleryImages;
  }

  /**
   * Returns a max per page value for retrieving Geocaches
   * @return max per page value for Geocaches
   */
  public int getGeocaches() {
    return geocaches;
  }

  /**
   * Returns a max per page value for retrieving Geocache logs
   * @return max per page value for Geocache logs
   */
  public int getGeocacheLogs() {
    return geocacheLogs;
  }

  /**
   * Returns a max per page value for retrieving Trackables
   * @return max per page value for Trackables
   */
  public int getTrackables() {
    return trackables;
  }

  /**
   * Returns a max per page value for retrieving Trackable logs
   * @return max per page value for Trackable logs
   */
  public int getTrackableLogs() {
    return trackableLogs;
  }

  /**
   * Returns a max per page value for retrieving Cache notes
   * @return max per page value for Cache notes
   */
  public int getCacheNotes() {
    return cacheNotes;
  }

  /**
   * Returns a max per page value for retrieving Gallery images
   * @return max per page value for Gallery images
   */
  public int getGalleryImages() {
    return galleryImages;
  }

  @Override
  public String toString() {
    return DebugUtils.toString(this);
  }
}
