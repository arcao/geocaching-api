package com.arcao.geocaching.api.impl.live_geocaching_api.parser;

import java.io.IOException;

import com.arcao.geocaching.api.data.GeocacheLimits;

public class CacheLimitsJsonParser extends JsonParser {
  public static GeocacheLimits parse(JsonReader r) throws IOException {
    int cacheLeft = 0;
    int currentCacheCount = 0;
    int maxCacheCount = 0;
    
    r.beginObject();
    while (r.hasNext()) {
      String name = r.nextName();
      if ("CacheLeft".equals(name)) {
        cacheLeft = r.nextInt();
      } else if ("CurrentCacheCount".equals(name)) {
        currentCacheCount = r.nextInt();
      } else if ("MaxCacheCount".equals(name)) {
        maxCacheCount = r.nextInt();
      } else {
        r.skipValue();
      }
    }
    r.endObject();

    return new GeocacheLimits(cacheLeft, currentCacheCount, maxCacheCount);
  }
}
