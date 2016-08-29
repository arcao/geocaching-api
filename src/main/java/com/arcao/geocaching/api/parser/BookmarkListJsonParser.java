package com.arcao.geocaching.api.parser;

import com.arcao.geocaching.api.data.bookmarks.BookmarkList;
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

    List<BookmarkList> list = new ArrayList<BookmarkList>();
    r.beginArray();
    while(r.hasNext()) {
      list.add(parse(r));
    }
    r.endArray();

    return list;
  }

  private static BookmarkList parse(JsonReader r) throws IOException {
    BookmarkList.Builder builder = BookmarkList.builder();

    r.beginObject();
    while(r.hasNext()) {
      String fieldName = r.nextName();
      if ("ListID".equals(fieldName)) {
        builder.id(r.nextInt());
      } else if ("ListGUID".equals(fieldName)) {
        builder.guid(r.nextString());
      } else if ("ListName".equals(fieldName)) {
        builder.name(r.nextString());
      } else if ("ListDescription".equals(fieldName)) {
        builder.description(r.nextString());
      } else if ("NumberOfItems".equals(fieldName)) {
        builder.itemCount(r.nextInt());
      } else if ("ListIsShared".equals(fieldName)) {
        builder.shared(r.nextBoolean());
      } else if ("ListIsPublic".equals(fieldName)) {
        builder.publicList(r.nextBoolean());
      } else if ("ListIsArchived".equals(fieldName)) {
        builder.archived(r.nextBoolean());
      } else if ("ListIsSpecial".equals(fieldName)) {
        builder.special(r.nextBoolean());
      } else if ("ListTypeID".equals(fieldName)) {
        builder.type(r.nextInt());
      } else {
        r.skipValue();
      }
    }
    r.endObject();

    return builder.build();
  }
}
