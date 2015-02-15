package com.arcao.geocaching.api.impl.live_geocaching_api.filter;

import java.io.IOException;

import com.arcao.geocaching.api.data.type.ContainerType;
import com.google.gson.stream.JsonWriter;

public class GeocacheContainerSizeFilter implements Filter {
	private static final String NAME = "GeocacheContainerSize";
	
	protected final ContainerType[] containerTypes;

	public GeocacheContainerSizeFilter(ContainerType... containerTypes) {
	  this.containerTypes = containerTypes;
  }
	
	public String getName() {
	  return NAME;
  }

	public boolean isValid() {
		if (containerTypes == null || containerTypes.length == 0)
			return false;
		
		boolean valid = false;
		for (ContainerType containerType : containerTypes) {
			if (containerType != null)
				valid = true;
		}
		
		return valid;
  }

	public void writeJson(JsonWriter w) throws IOException {
		w.name(NAME);
		w.beginObject();
		w.name("GeocacheContainerSizeIds");
		w.beginArray();
		for (ContainerType containerType : containerTypes) {
			if (containerType != null)
				w.value(containerType.getId());
		}
		w.endArray();
		w.endObject();
  }
}
