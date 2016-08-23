package com.arcao.geocaching.api.impl.live_geocaching_api.parser;

import com.arcao.geocaching.api.data.apilimits.MaxPerPage;

import java.io.IOException;

public class MaxPerPageJsonParser extends JsonParser {
    public static MaxPerPage parse(JsonReader r) throws IOException {
        MaxPerPage.Builder builder = MaxPerPage.builder();

        r.beginObject();
        while (r.hasNext()) {
            String name = r.nextName();
            if ("Geocaches".equals(name)) {
                builder.geocaches(r.nextInt());
            } else if ("GeocacheLogs".equals(name)) {
                builder.geocacheLogs(r.nextInt());
            } else if ("Trackables".equals(name)) {
                builder.trackables(r.nextInt());
            } else if ("TrackableLogs".equals(name)) {
                builder.trackableLogs(r.nextInt());
            } else if ("CacheNotes".equals(name)) {
                builder.cacheNotes(r.nextInt());
            } else if ("GalleryImages".equals(name)) {
                builder.galleryImages(r.nextInt());
            } else {
                r.skipValue();
            }
        }
        r.endObject();

        return builder.build();
    }
}
