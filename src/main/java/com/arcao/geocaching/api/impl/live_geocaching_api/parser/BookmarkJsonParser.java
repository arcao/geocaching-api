package com.arcao.geocaching.api.impl.live_geocaching_api.parser;

import com.arcao.geocaching.api.data.bookmarks.Bookmark;
import com.arcao.geocaching.api.data.bookmarks.BookmarkList;
import com.arcao.geocaching.api.data.type.GeocacheType;
import com.google.gson.stream.JsonToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BookmarkJsonParser extends JsonParser {
  public static List<Bookmark> parseList(JsonReader r) throws IOException {
    if (r.peek() != JsonToken.BEGIN_ARRAY) {
      r.skipValue();
    }

    List<Bookmark> list = new ArrayList<Bookmark>();
    r.beginArray();
    while(r.hasNext()) {
      list.add(parse(r));
    }
    r.endArray();

    return list;
  }

  public static Bookmark parse(JsonReader r) throws IOException {
    String cacheCode = "";
    String cacheTitle = "";
    GeocacheType geocacheType = null;

    r.beginObject();
    while(r.hasNext()) {
      String fieldName = r.nextName();
      if ("CacheCode".equals(fieldName)) {
        cacheCode = r.nextString();
      } else if ("CacheTitle".equals(fieldName)) {
        cacheTitle = r.nextString();
      } else if ("CacheTypeID".equals(fieldName)) {
        geocacheType = GeocacheType.getById(r.nextInt());
      } else {
        r.skipValue();
      }
    }
    r.endObject();

    return new Bookmark(cacheCode, cacheTitle, geocacheType);
  }
}