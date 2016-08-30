package com.arcao.geocaching.api.filter;

import com.arcao.geocaching.api.data.coordinates.Coordinates;
import com.google.gson.stream.JsonWriter;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class RecommendedFilter implements Filter {
  private static final String NAME = "Recommended";

  private final double latitude;
  private final double longitude;

  public RecommendedFilter(@NotNull Coordinates origin) {
    latitude = origin.latitude();
    longitude = origin.longitude();
  }

  public RecommendedFilter(double latitude, double longitude) {
    this.latitude = latitude;
    this.longitude = longitude;
  }

  @NotNull
  @Override
  public String name() {
    return NAME;
  }

  @Override
  public boolean valid() {
    return !Double.isNaN(latitude) && !Double.isNaN(longitude);
  }

  @Override
  public void writeJson(@NotNull JsonWriter w) throws IOException {
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
