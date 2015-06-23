package com.arcao.geocaching.api.data.bookmarks;

import com.arcao.geocaching.api.data.type.GeocacheType;
import com.arcao.geocaching.api.util.DebugUtils;

import java.io.Serializable;

public class Bookmark implements Serializable {
  private static final long serialVersionUID = -2198982416375777824L;

  private final String cacheCode;
  private final String cacheTitle;
  private final GeocacheType geocacheType;

  public Bookmark(String cacheCode, String cacheTitle, GeocacheType geocacheType) {
    this.cacheCode = cacheCode;
    this.cacheTitle = cacheTitle;
    this.geocacheType = geocacheType;
  }

  public String getCacheCode() {
    return cacheCode;
  }

  public String getCacheTitle() {
    return cacheTitle;
  }

  public GeocacheType getGeocacheType() {
    return geocacheType;
  }

  @Override
  public String toString() {
    return DebugUtils.toString(this);
  }
}
