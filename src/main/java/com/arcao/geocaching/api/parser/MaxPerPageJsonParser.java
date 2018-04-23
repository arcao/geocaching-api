package com.arcao.geocaching.api.parser;

import com.arcao.geocaching.api.data.apilimits.MaxPerPage;
import com.google.gson.stream.JsonReader;

import java.io.IOException;

public final class MaxPerPageJsonParser {
    private MaxPerPageJsonParser() {
    }

    public static MaxPerPage parse(JsonReader r) throws IOException {
        MaxPerPage.Builder builder = MaxPerPage.builder();

        r.beginObject();
        while (r.hasNext()) {
            String name = r.nextName();
            switch (name) {
                case "Geocaches":
                    builder.geocaches(r.nextInt());
                    break;
                case "GeocacheLogs":
                    builder.geocacheLogs(r.nextInt());
                    break;
                case "Trackables":
                    builder.trackables(r.nextInt());
                    break;
                case "TrackableLogs":
                    builder.trackableLogs(r.nextInt());
                    break;
                case "CacheNotes":
                    builder.cacheNotes(r.nextInt());
                    break;
                case "GalleryImages":
                    builder.galleryImages(r.nextInt());
                    break;
                default:
                    r.skipValue();
                    break;
            }
        }
        r.endObject();

        return builder.build();
    }
}
