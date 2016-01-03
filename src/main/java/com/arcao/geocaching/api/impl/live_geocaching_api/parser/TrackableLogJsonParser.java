package com.arcao.geocaching.api.impl.live_geocaching_api.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.arcao.geocaching.api.data.ImageData;
import com.arcao.geocaching.api.data.Trackable;
import com.arcao.geocaching.api.data.TrackableLog;
import com.arcao.geocaching.api.data.User;
import com.arcao.geocaching.api.data.coordinates.Coordinates;
import com.arcao.geocaching.api.data.type.TrackableLogType;
import com.google.gson.stream.JsonToken;

public class TrackableLogJsonParser extends JsonParser {
  public static List<TrackableLog> parseList(JsonReader r) throws IOException {
    if (r.peek() != JsonToken.BEGIN_ARRAY) {
      r.skipValue();
    }
    
    List<TrackableLog> list = new ArrayList<TrackableLog>();
    r.beginArray();
    while(r.hasNext()) {
      list.add(parse(r));
    }
    r.endArray();
    
    return list;
  }
  
  public static TrackableLog parse(JsonReader r) throws IOException {
    TrackableLog.Builder trackableLog = TrackableLog.Builder.trackableLog();
    Coordinates.Builder updatedCoordinates = Coordinates.Builder.coordinates();

    r.beginObject();
    while(r.hasNext()) {
      String name = r.nextName();
      if ("CacheID".equals(name)) {
        trackableLog.withCacheID(r.nextInt());
      } else if ("Code".equals(name)) {
        trackableLog.withCode(r.nextString());
      } else if ("ID".equals(name)) {
        trackableLog.withId(r.nextInt());
      } else if ("Images".equals(name)) {
        trackableLog.withImages(ImageDataJsonParser.parseList(r));
      } else if ("IsArchived".equals(name)) {
        trackableLog.withArchived(r.nextBoolean());
      } else if ("LogGuid".equals(name)) {
        trackableLog.withGuid(r.nextString());
      } else if ("LogText".equals(name)) {
        trackableLog.withText(r.nextString());
      } else if ("LogType".equals(name)) {
        trackableLog.withType( parseTrackableLogType(r));
      } else if ("LoggedBy".equals(name)) {
        trackableLog.withLoggedBy(parseUser(r));
      } else if ("UTCCreateDate".equals(name)) {
        trackableLog.withCreated(parseJsonUTCDate(r.nextString()));
      } else if ("UpdatedLatitude".equals(name)) {
        updatedCoordinates.withLatitude(r.nextDouble());
      } else if ("UpdatedLongitude".equals(name)) {
        updatedCoordinates.withLongitude(r.nextDouble());
      } else if ("Url".equals(name)) {
        trackableLog.withUrl(r.nextString());
      } else if ("VisitDate".equals(name)) {
        trackableLog.withVisited(parseJsonDate(r.nextString()));
      } else {
        r.skipValue();
      }
    }
    r.endObject();

    trackableLog.withUpdatedCoordinates(updatedCoordinates.build());

    return trackableLog.build();
  }
}
