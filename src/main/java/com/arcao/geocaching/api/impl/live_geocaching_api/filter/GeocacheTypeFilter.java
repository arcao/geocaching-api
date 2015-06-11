package com.arcao.geocaching.api.impl.live_geocaching_api.filter;


import java.io.IOException;

import com.arcao.geocaching.api.data.type.GeocacheType;
import com.google.gson.stream.JsonWriter;

public class GeocacheTypeFilter implements Filter {
	private static final String NAME = "GeocacheType";

	protected final GeocacheType[] geocacheTypes;
	
	public GeocacheTypeFilter(GeocacheType... geocacheTypes) {
		this.geocacheTypes = geocacheTypes;
	}
	
	public boolean isValid() {
		if (geocacheTypes == null || geocacheTypes.length == 0)
			return false;
		
		boolean valid = false;
		for (GeocacheType geocacheType : geocacheTypes) {
			if (geocacheType != null)
				valid = true;
		}
		
		return valid;
	}
	
	public void writeJson(JsonWriter w) throws IOException {
		w.name(NAME);
		w.beginObject();
		w.name("GeocacheTypeIds");
		w.beginArray();
		for (GeocacheType geocacheType : geocacheTypes) {
			if (geocacheType != null)
				w.value(geocacheType.getId());
		}
		w.endArray();
		w.endObject();
	}

	public String getName() {
		return NAME;
	}

}
