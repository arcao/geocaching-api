package com.arcao.geocaching.api.parser;

import com.arcao.geocaching.api.data.bookmarks.BookmarkList;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class BookmarkListJsonParser {
    private BookmarkListJsonParser() {
    }

    public static List<BookmarkList> parseList(JsonReader r) throws IOException {
        if (r.peek() != JsonToken.BEGIN_ARRAY) {
            r.skipValue();
        }

        List<BookmarkList> list = new ArrayList<>();
        r.beginArray();
        while (r.hasNext()) {
            list.add(parse(r));
        }
        r.endArray();

        return list;
    }

    private static BookmarkList parse(JsonReader r) throws IOException {
        BookmarkList.Builder builder = BookmarkList.builder();

        r.beginObject();
        while (r.hasNext()) {
            String fieldName = r.nextName();
            switch (fieldName) {
                case "ListID":
                    builder.id(r.nextInt());
                    break;
                case "ListGUID":
                    builder.guid(r.nextString());
                    break;
                case "ListName":
                    builder.name(r.nextString());
                    break;
                case "ListDescription":
                    builder.description(r.nextString());
                    break;
                case "NumberOfItems":
                    builder.itemCount(r.nextInt());
                    break;
                case "ListIsShared":
                    builder.shared(r.nextBoolean());
                    break;
                case "ListIsPublic":
                    builder.publicList(r.nextBoolean());
                    break;
                case "ListIsArchived":
                    builder.archived(r.nextBoolean());
                    break;
                case "ListIsSpecial":
                    builder.special(r.nextBoolean());
                    break;
                case "ListTypeID":
                    builder.type(r.nextInt());
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
