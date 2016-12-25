package com.arcao.geocaching.api.parser;

import com.arcao.geocaching.api.data.GeocacheLimits;

import java.io.IOException;

public final class CacheLimitsJsonParser {
    private CacheLimitsJsonParser() {
    }

    public static GeocacheLimits parse(JsonReader r) throws IOException {
        GeocacheLimits.Builder builder = GeocacheLimits.builder();

        r.beginObject();
        while (r.hasNext()) {
            String name = r.nextName();
            if ("CachesLeft".equals(name)) {
                builder.geocacheLeft(r.nextInt());
            } else if ("CurrentCacheCount".equals(name)) {
                builder.currentGeocacheCount(r.nextInt());
            } else if ("MaxCacheCount".equals(name)) {
                builder.maxGeocacheCount(r.nextInt());
            } else {
                r.skipValue();
            }
        }
        r.endObject();

        return builder.build();
    }
}
