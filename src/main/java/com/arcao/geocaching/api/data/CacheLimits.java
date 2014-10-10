package com.arcao.geocaching.api.data;

import com.arcao.geocaching.api.util.DebugUtils;

import java.io.Serializable;

/**
 * Container for current state of Geocache retrieve limit information
 * @author arcao
 *
 */
public class CacheLimits implements Serializable {
  private static final long serialVersionUID = 907830786611718961L;

  private final int cacheLeft;
  private final int currentCacheCount;
  private final int maxCacheCount;

  public CacheLimits(int cacheLeft, int currentCacheCount, int maxCacheCount) {
    this.cacheLeft = cacheLeft;
    this.currentCacheCount = currentCacheCount;
    this.maxCacheCount = maxCacheCount;
  }
  
  /**
   * Returns the count of remain caches in a limit
   * @return remain caches
   */
  public int getCacheLeft() {
    return cacheLeft;
  }
  
  /**
   * Returns the count of caches which was used from a limit
   * @return count of caches which was used from a limit
   */
  public int getCurrentCacheCount() {
    return currentCacheCount;
  }
  
  /**
   * Returns the count of caches in a limit
   * @return count of caches in a limit
   */
  public int getMaxCacheCount() {
    return maxCacheCount;
  }
  
	@Override
	public String toString() {
		return DebugUtils.toString(this);
	}
}
