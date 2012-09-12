package com.arcao.geocaching.api.impl.live_geocaching_api.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.arcao.geocaching.api.data.ImageData;
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
    int cacheID = 0;
    String code = null;
    int id = 0;
    List<ImageData> images = new ArrayList<ImageData>();
    boolean archived = false;
    String guid = null;
    String text = null;
    TrackableLogType type = TrackableLogType.WriteNote;
    User loggedBy = User.EMPTY;
    Date createDate = null;
    double updatedLatitude = Double.NaN;
    double updatedLongitude = Double.NaN;
    String url = null;
    Date visitDate = null;

    
    r.beginObject();
    while(r.hasNext()) {
      String name = r.nextName();
      if ("CacheId".equals(name)) {
        cacheID = r.nextInt();
      } else if ("Code".equals(name)) {
        code = r.nextString();
      } else if ("ID".equals(name)) {
        id = r.nextInt();
      } else if ("Images".equals(name)) {
        images = ImageDataJsonParser.parseList(r);
      } else if ("IsArchived".equals(name)) {
        archived = r.nextBoolean();
      } else if ("LogGuid".equals(name)) {
        guid = r.nextString();
      } else if ("LogText".equals(name)) {
        text = r.nextString();
      } else if ("LogType".equals(name)) {
        type = parseTrackableLogType(r);
      } else if ("LoggedBy".equals(name)) {
        loggedBy = parseUser(r);
      } else if ("UTCCreateDate".equals(name)) {
        createDate = parseJsonUTCDate(r.nextString());
      } else if ("UpdatedLatitude".equals(name)) {
        updatedLatitude = r.nextDouble();
      } else if ("UpdatedLongitude".equals(name)) {
        updatedLongitude = r.nextDouble();
      } else if ("Url".equals(name)) {
        url = r.nextString();
      } else if ("VisitDate".equals(name)) {
        visitDate = parseJsonDate(r.nextString());
      } else {
        r.skipValue();
      }
    }
    r.endObject();

    return new TrackableLog(cacheID, code, id, images, archived, guid, text, type, loggedBy, createDate, new Coordinates(updatedLatitude, updatedLongitude), url, visitDate);
  }
}
