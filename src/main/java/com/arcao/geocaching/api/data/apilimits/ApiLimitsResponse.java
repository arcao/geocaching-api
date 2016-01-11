package com.arcao.geocaching.api.data.apilimits;

import com.arcao.geocaching.api.util.DebugUtils;

import java.io.Serializable;

/**
 * Container class for an ApiLimits method response
 * @author arcao
 */
public class ApiLimitsResponse implements Serializable {
  private static final long serialVersionUID = 2753514511831397947L;

  private final ApiLimits apiLimits;
  private final MaxPerPage maxPerPage;

  public ApiLimitsResponse(ApiLimits apiLimits, MaxPerPage maxPerPage) {
    this.apiLimits = apiLimits;
    this.maxPerPage = maxPerPage;
  }

  /**
   * Returns an api limits
   * @return api limits
   */
  public ApiLimits getApiLimits() {
    return apiLimits;
  }

  /**
   * Returns an information about max per page values
   * @return max per page values
   */
  public MaxPerPage getMaxPerPage() {
    return maxPerPage;
  }

  @Override
  public String toString() {
    return DebugUtils.toString(this);
  }
}
