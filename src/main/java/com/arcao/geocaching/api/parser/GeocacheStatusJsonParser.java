package com.arcao.geocaching.api.parser;

import com.arcao.geocaching.api.data.GeocacheStatus;
import com.arcao.geocaching.api.data.type.GeocacheType;
import com.google.gson.stream.JsonReader;
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

        List<GeocacheStatus> list = new ArrayList<>();
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
            switch (name) {
                case "Archived":
                    builder.archived(r.nextBoolean());
                    break;
                case "Available":
                    builder.available(r.nextBoolean());
                    break;
                case "CacheCode":
                    builder.cacheCode(r.nextString());
                    break;
                case "CacheName":
                    builder.cacheName(r.nextString());
                    break;
                case "CacheType":
                    builder.cacheType(GeocacheType.fromId(r.nextInt()));
                    break;
                case "Premium":
                    builder.premium(r.nextBoolean());
                    break;
                case "TrackableCount":
                    builder.trackableCount(r.nextInt());
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
