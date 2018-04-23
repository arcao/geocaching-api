package com.arcao.geocaching.api.parser;

import com.arcao.geocaching.api.data.bookmarks.Bookmark;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.arcao.geocaching.api.data.type.GeocacheType.fromId;

public final class BookmarkJsonParser {
    private BookmarkJsonParser() {
    }

    public static List<Bookmark> parseList(JsonReader r) throws IOException {
        if (r.peek() != JsonToken.BEGIN_ARRAY) {
            r.skipValue();
        }

        List<Bookmark> list = new ArrayList<>();
        r.beginArray();
        while (r.hasNext()) {
            list.add(parse(r));
        }
        r.endArray();

        return list;
    }

    private static Bookmark parse(JsonReader r) throws IOException {
        Bookmark.Builder builder = Bookmark.builder();

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
                case "CacheTypeID":
                    builder.geocacheType(fromId(r.nextInt()));
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