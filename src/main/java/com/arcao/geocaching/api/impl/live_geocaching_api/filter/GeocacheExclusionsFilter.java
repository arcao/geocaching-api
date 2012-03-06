package com.arcao.geocaching.api.impl.live_geocaching_api.filter;

import java.io.IOException;

import com.google.gson.stream.JsonWriter;

public class GeocacheExclusionsFilter implements Filter {
	private static final String NAME = "GeocacheExclusions";
	
	private final Boolean archived;
	private final Boolean available;
	private final Boolean premium;
	
	public GeocacheExclusionsFilter(Boolean archived, Boolean available, Boolean premium) {
		this.archived = archived;
		this.available = available;
		this.premium = premium;
	}
	
	public boolean isValid() {
		return archived != null || available != null || premium != null;
	}
	
	public void writeJson(JsonWriter w) throws IOException {
		w.name(NAME);
		w.beginObject();
		if (archived != null)
			w.name("Archived").value(archived);
		if (available != null)
			w.name("Available").value(available);
		if (premium != null)
			w.name("Premium").value(premium);
		w.endObject();
	}

	public String getName() {
		return NAME;
	}

}