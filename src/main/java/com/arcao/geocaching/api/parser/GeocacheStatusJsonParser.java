package com.arcao.geocaching.api.parser;

import com.arcao.geocaching.api.data.GeocacheStatus;
import com.arcao.geocaching.api.data.type.GeocacheType;
import com.google.gson.stream.JsonToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Krzysztof.Nowacki on 2015.11.13.
 */
public final class GeocacheStatusJsonParser {
    private GeocacheStatusJsonParser() {
    }

    public static List<GeocacheStatus> parseList(JsonReader r) throws IOException {
        if (r.peek() != JsonToken.BEGIN_ARRAY) {
            r.skipValue();
        }

        List<GeocacheStatus> list = new ArrayList<GeocacheStatus>();
        r.beginArray();
        while (r.hasNext()) {
            list.add(parse(r));
        }
        r.endArray();
        return list;
    }

    private static GeocacheStatus parse(JsonReader r) throws IOException {
        GeocacheStatus.Builder builder = GeocacheStatus.builder();

        r.beginObject();
        while (r.hasNext()) {
            String name = r.nextName();
            if ("Archived".equals(name)) {
                builder.archived(r.nextBoolean());
            } else if ("Available".equals(name)) {
                builder.available(r.nextBoolean());
            } else if ("CacheCode".equals(name)) {
                builder.cacheCode(r.nextString());
            } else if ("CacheName".equals(name)) {
                builder.cacheName(r.nextString());
            } else if ("CacheType".equals(name)) {
                builder.cacheType(GeocacheType.fromId(r.nextInt()));
            } else if ("Premium".equals(name)) {
                builder.premium(r.nextBoolean());
            } else if ("TrackableCount".equals(name)) {
                builder.trackableCount(r.nextInt());
            } else {
                r.skipValue();
            }
        }
        r.endObject();

        return builder.build();
    }
}
