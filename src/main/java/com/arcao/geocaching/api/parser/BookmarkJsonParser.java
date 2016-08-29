package com.arcao.geocaching.api.parser;

import com.arcao.geocaching.api.data.bookmarks.Bookmark;
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

        List<Bookmark> list = new ArrayList<Bookmark>();
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
            if ("CacheCode".equals(fieldName)) {
                builder.cacheCode(r.nextString());
            } else if ("CacheTitle".equals(fieldName)) {
                builder.cacheTitle(r.nextString());
            } else if ("CacheTypeID".equals(fieldName)) {
                builder.geocacheType(fromId(r.nextInt()));
            } else {
                r.skipValue();
            }
        }
        r.endObject();

        return builder.build();
    }
}