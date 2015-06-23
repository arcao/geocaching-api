package com.arcao.geocaching.api.impl.live_geocaching_api.parser;

import com.arcao.geocaching.api.data.bookmarks.BookmarkList;
import com.google.gson.stream.JsonToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BookmarkListJsonParser extends JsonParser {
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

  public static BookmarkList parse(JsonReader r) throws IOException {
    int id = 0;
    String guid = "";
    String name = "";
    String description = "";
    int itemCount = 0;
    boolean shared = false;
    boolean isPublic = false;
    boolean archived = false;
    boolean special = false;
    int type = 0;

    r.beginObject();
    while(r.hasNext()) {
      String fieldName = r.nextName();
      if ("ListID".equals(fieldName)) {
        id = r.nextInt();
      } else if ("ListGUID".equals(fieldName)) {
        guid = r.nextString();
      } else if ("ListName".equals(fieldName)) {
        name = r.nextString();
      } else if ("ListDescription".equals(fieldName)) {
        description = r.nextString();
      } else if ("NumberOfItems".equals(fieldName)) {
        itemCount = r.nextInt();
      } else if ("ListIsShared".equals(fieldName)) {
        shared = r.nextBoolean();
      } else if ("ListIsPublic".equals(fieldName)) {
        isPublic = r.nextBoolean();
      } else if ("ListIsArchived".equals(fieldName)) {
        archived = r.nextBoolean();
      } else if ("ListIsSpecial".equals(fieldName)) {
        special = r.nextBoolean();
      } else if ("ListTypeID".equals(fieldName)) {
        type = r.nextInt();
      } else {
        r.skipValue();
      }
    }
    r.endObject();

    return new BookmarkList(id, guid, name, description, itemCount, shared, isPublic, archived, special, type);
  }
}
