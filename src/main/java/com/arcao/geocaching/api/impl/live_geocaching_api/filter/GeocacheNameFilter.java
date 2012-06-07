package com.arcao.geocaching.api.impl.live_geocaching_api.filter;

import java.io.IOException;

import com.google.gson.stream.JsonWriter;

public class GeocacheNameFilter implements Filter {
	private static final String NAME = "GeocacheName";
	
	protected final String name;
	
	public GeocacheNameFilter(String name) {
	  this.name = name;
  }

	public String getName() {
	  return NAME;
  }

	public boolean isValid() {
	  return name != null && name.length() > 0;
  }

	public void writeJson(JsonWriter w) throws IOException {
		w.name(NAME);
		w.beginObject();
		w.name("GeocacheName").value(name);
		w.endObject();
	}
}
