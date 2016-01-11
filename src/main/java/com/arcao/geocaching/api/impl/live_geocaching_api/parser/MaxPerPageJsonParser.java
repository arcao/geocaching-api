package com.arcao.geocaching.api.impl.live_geocaching_api.parser;

import com.arcao.geocaching.api.data.apilimits.MaxPerPage;

import java.io.IOException;

public class MaxPerPageJsonParser extends JsonParser {
  public static MaxPerPage parse(JsonReader r) throws IOException {
    int geocaches = 0;
    int geocacheLogs = 0;
    int trackables = 0;
    int trackableLogs = 0;
    int cacheNotes = 0;
    int galleryImages = 0;

    r.beginObject();
    while (r.hasNext()) {
      String name = r.nextName();
      if ("Geocaches".equals(name)) {
        geocaches = r.nextInt();
      } else if ("GeocacheLogs".equals(name)) {
        geocacheLogs = r.nextInt();
      } else if ("Trackables".equals(name)) {
        trackables = r.nextInt();
      } else if ("TrackableLogs".equals(name)) {
        trackableLogs = r.nextInt();
      } else if ("CacheNotes".equals(name)) {
        cacheNotes = r.nextInt();
      } else if ("GalleryImages".equals(name)) {
        galleryImages = r.nextInt();
      } else {
        r.skipValue();
      }
    }
    r.endObject();

    return new MaxPerPage(geocaches, geocacheLogs, trackables, trackableLogs, cacheNotes, galleryImages);
  }
}
