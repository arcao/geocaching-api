package com.arcao.geocaching.api.impl.live_geocaching_api.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.arcao.geocaching.api.data.TrackableTravel;
import com.arcao.geocaching.api.data.coordinates.Coordinates;
import com.google.gson.stream.JsonToken;

public class TrackableTravelJsonParser  extends JsonParser {
  public static List<TrackableTravel> parseList(JsonReader r) throws IOException {
    if (r.peek() != JsonToken.BEGIN_ARRAY) {
      r.skipValue();
    }
    
    List<TrackableTravel> list = new ArrayList<TrackableTravel>();
    r.beginArray();
    while(r.hasNext()) {
      list.add(parse(r));
    }
    r.endArray();
    
    return list;
  }
  
  public static TrackableTravel parse(JsonReader r) throws IOException {
    int cacheID = 0;
    Date dateLogged = null;
    double latitude = Double.NaN;
    double longitude = Double.NaN;
    
    r.beginObject();
    while(r.hasNext()) {
      String name = r.nextName();
      if ("CacheID".equals(name)) {
        cacheID = r.nextInt();
      } else if ("DateLogged".equals(name)) {
      	dateLogged = parseJsonDate(r.nextString());
      } else if ("Latitude".equals(name)) {
        latitude = r.nextDouble();
      } else if ("Longitude".equals(name)) {
        longitude = r.nextDouble();
      } else {
        r.skipValue();
      }
    }
    r.endObject();

    return new TrackableTravel(cacheID, dateLogged, new Coordinates(latitude, longitude));
  }
}
