package com.arcao.geocaching.api.impl.live_geocaching_api.filter;

import java.io.IOException;

import com.google.gson.stream.JsonWriter;

public class FavoritePointsFilter implements Filter {
	private static final String NAME = "FavoritePoints";

	protected final Integer min;
	protected final Integer max;
	
	public FavoritePointsFilter(Integer min, Integer max) {
	  this.min = min;
	  this.max = max;
  }

	public String getName() {
	  return NAME;
  }

	public boolean isValid() {
		if (min != null && max != null && min.intValue() > max.intValue())
			return false;
		
	  return (min != null || max != null);
  }
	
	public void writeJson(JsonWriter w) throws IOException {
		w.name(NAME);
		w.beginObject();
		if (min != null)
			w.name("MinFavoritePoints").value(min.intValue());
		if (max != null)
			w.name("MaxFavoritePoints").value(max.intValue());
		w.endObject();
  }
}
