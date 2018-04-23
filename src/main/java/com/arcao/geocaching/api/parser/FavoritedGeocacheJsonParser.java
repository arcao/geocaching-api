package com.arcao.geocaching.api.parser;

import com.arcao.geocaching.api.data.FavoritedGeocache;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class FavoritedGeocacheJsonParser {
    private FavoritedGeocacheJsonParser() {
    }

    public static List<FavoritedGeocache> parseList(JsonReader r) throws IOException {
        if (r.peek() != JsonToken.BEGIN_ARRAY) {
            r.skipValue();
        }

        List<FavoritedGeocache> list = new ArrayList<>();
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
            switch (fieldName) {
                case "CacheCode":
                    builder.cacheCode(r.nextString());
                    break;
                case "CacheTitle":
                    builder.cacheTitle(r.nextString());
                    break;
                case "CacheType":
                    builder.geocacheType(JsonParserUtil.parseGeocacheType(r));
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