package com.arcao.geocaching.api.data.apilimits;

/**
 * Container class for a cache limit information
 * @author arcao
 */
public class CacheLimit {
  private final long limit;
  private final long period;
  
  public CacheLimit(long limit, long period) {
    this.limit = limit;
    this.period = period;
  }
  
  /**
   * Returns a cache limit 
   * @return limit
   */
  public long getLimit() {
    return limit;
  }
  
  /**
   * Returns a period for this limit in the minutes
   * @return period in minutes
   */
  public long getPeriod() {
    return period;
  }
}
