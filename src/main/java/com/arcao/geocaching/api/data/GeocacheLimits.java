package com.arcao.geocaching.api.data;

import com.arcao.geocaching.api.util.DebugUtils;

import java.io.Serializable;

/**
 * Container for current state of Geocache retrieve limit information
 * @author arcao
 *
 */
public class GeocacheLimits implements Serializable {
  private static final long serialVersionUID = 907830786611718961L;

  private final int geocacheLeft;
  private final int currentGeocacheCount;
  private final int maxGeocacheCount;

  public GeocacheLimits(int geocacheLeft, int currentGeocacheCount, int maxGeocacheCount) {
    this.geocacheLeft = geocacheLeft;
    this.currentGeocacheCount = currentGeocacheCount;
    this.maxGeocacheCount = maxGeocacheCount;
  }
  
  /**
   * Returns the count of remain geocaches in a limit
   * @return remain caches
   */
  public int getGeocacheLeft() {
    return geocacheLeft;
  }
  
  /**
   * Returns the count of geocaches which was used from a limit
   * @return count of caches which was used from a limit
   */
  public int getCurrentGeocacheCount() {
    return currentGeocacheCount;
  }
  
  /**
   * Returns the count of geocaches in a limit
   * @return count of caches in a limit
   */
  public int getMaxGeocacheCount() {
    return maxGeocacheCount;
  }
  
	@Override
	public String toString() {
		return DebugUtils.toString(this);
	}
}
