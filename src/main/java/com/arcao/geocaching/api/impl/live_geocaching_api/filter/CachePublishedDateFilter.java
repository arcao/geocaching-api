package com.arcao.geocaching.api.impl.live_geocaching_api.filter;

import com.arcao.geocaching.api.impl.live_geocaching_api.builder.JsonBuilder;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.Date;

public class CachePublishedDateFilter implements Filter {
  private static final String NAME = "CachePublishedDate";

  private final Date startDate;
  private final Date endDate;

  public CachePublishedDateFilter(Date startDate, Date endDate) {
    this.startDate = startDate;
    this.endDate = endDate;
  }

  public String getName() {
    return NAME;
  }

  public boolean isValid() {
    return startDate != null || endDate != null;
  }

  public void writeJson(JsonWriter w) throws IOException {
    w.name(NAME);
    w.beginObject();
    w.name("Range");
    w.beginObject();
    if (startDate != null)
      w.name("StartDate").value(JsonBuilder.dateToJsonString(startDate));
    if (endDate != null)
      w.name("EndDate").value(JsonBuilder.dateToJsonString(endDate));
    w.endObject();
    w.endObject();
  }

}
