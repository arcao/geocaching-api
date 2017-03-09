package com.arcao.geocaching.api.parser;

import com.arcao.geocaching.api.data.FavoritedGeocache;
import com.google.gson.stream.JsonToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.arcao.geocaching.api.data.type.GeocacheType.fromId;

public final class FavoritedGeocacheJsonParser {
    private FavoritedGeocacheJsonParser() {
    }

    public static List<FavoritedGeocache> parseList(JsonReader r) throws IOException {
        if (r.peek() != JsonToken.BEGIN_ARRAY) {
            r.skipValue();
        }

        List<FavoritedGeocache> list = new ArrayList<FavoritedGeocache>();
        r.beginArray();
        while (r.hasNext()) {
            list.add(parse(r));
        }
        r.endArray();

        return list;
    }

    private static FavoritedGeocache parse(JsonReader r) throws IOException {
        FavoritedGeocache.Builder builder = FavoritedGeocache.builder();

        r.beginObject();
        while (r.hasNext()) {
            String fieldName = r.nextName();
            if ("CacheCode".equals(fieldName)) {
                builder.cacheCode(r.nextString());
            } else if ("CacheTitle".equals(fieldName)) {
                builder.cacheTitle(r.nextString());
            } else if ("CacheType".equals(fieldName)) {
                builder.geocacheType(JsonParserUtil.parseGeocacheType(r));
            } else {
                r.skipValue();
            }
        }
        r.endObject();

        return builder.build();
    }
}