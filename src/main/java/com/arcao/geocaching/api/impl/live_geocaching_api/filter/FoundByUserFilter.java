package com.arcao.geocaching.api.impl.live_geocaching_api.filter;

import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class FoundByUserFilter implements Filter {
  private static final String NAME = "FoundByUser";

  private final String userName;

  public FoundByUserFilter(String userName) {
    this.userName = userName;
  }

  public String getName() {
    return NAME;
  }

  public boolean isValid() {
    return userName != null;
  }

  public void writeJson(JsonWriter w) throws IOException {
    w.name(NAME);
    w.beginObject();
    w.name("UserName").value(userName);
    w.endObject();
  }
}
