package com.arcao.geocaching.api.parser;

import com.arcao.geocaching.api.data.GeocacheLimits;
import com.google.gson.stream.JsonReader;

import java.io.IOException;

public final class CacheLimitsJsonParser {
    private CacheLimitsJsonParser() {
    }

    public static GeocacheLimits parse(JsonReader r) throws IOException {
        GeocacheLimits.Builder builder = GeocacheLimits.builder();

        r.beginObject();
        while (r.hasNext()) {
            String name = r.nextName();
            switch (name) {
                case "CachesLeft":
                    builder.geocacheLeft(r.nextInt());
                    break;
                case "CurrentCacheCount":
                    builder.currentGeocacheCount(r.nextInt());
                    break;
                case "MaxCacheCount":
                    builder.maxGeocacheCount(r.nextInt());
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
