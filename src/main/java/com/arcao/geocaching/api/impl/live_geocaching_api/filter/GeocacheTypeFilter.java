package com.arcao.geocaching.api.impl.live_geocaching_api.filter;


import java.io.IOException;

import com.arcao.geocaching.api.data.type.CacheType;
import com.google.gson.stream.JsonWriter;

public class GeocacheTypeFilter implements Filter {
	private static final String NAME = "GeocacheType";

	protected final CacheType[] cacheTypes;
	
	public GeocacheTypeFilter(CacheType... cacheTypes) {
		this.cacheTypes = cacheTypes;
	}
	
	public boolean isValid() {
		if (cacheTypes == null || cacheTypes.length == 0)
			return false;
		
		boolean valid = false;
		for (CacheType cacheType : cacheTypes) {
			if (cacheType != null)
				valid = true;
		}
		
		return valid;
	}
	
	public void writeJson(JsonWriter w) throws IOException {
		w.name(NAME);
		w.beginObject();
		w.name("GeocacheTypeIds");
		w.beginArray();
		for (CacheType cacheType : cacheTypes) {
			if (cacheType != null)
				w.value(cacheType.getGroundSpeakId());
		}
		w.endArray();
		w.endObject();
	}

	public String getName() {
		return NAME;
	}

}
