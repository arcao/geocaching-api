package com.arcao.geocaching.api.impl.live_geocaching_api.filter;

import com.arcao.geocaching.api.data.coordinates.Coordinates;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class RecommendedFilter implements Filter {
  private static final String NAME = "Recommended";

  protected final double latitude;
  protected final double longitude;

  public RecommendedFilter(Coordinates origin) {
    latitude = origin.latitude();
    longitude = origin.longitude();
  }

  public RecommendedFilter(double latitude, double longitude) {
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public String getName() {
    return NAME;
  }

  public boolean isValid() {
    return !Double.isNaN(latitude) && !Double.isNaN(longitude);
  }

  public void writeJson(JsonWriter w) throws IOException {
    w.name(NAME);
    w.beginObject();
    w.name("Origin");
    w.beginObject();
    w.name("Latitude").value(latitude);
    w.name("Longitude").value(longitude);
    w.endObject();
    w.endObject();
  }

}
