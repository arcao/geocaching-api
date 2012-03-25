package com.arcao.geocaching.api.impl.live_geocaching_api.parser;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.arcao.geocaching.api.data.Trackable;
import com.arcao.geocaching.api.data.User;
import com.arcao.geocaching.api.data.coordinates.Coordinates;
import com.arcao.geocaching.api.data.type.MemberType;
import com.google.gson.stream.JsonToken;

public class TrackableJsonParser extends JsonParser {
	public static List<Trackable> parseList(JsonReader r) throws IOException {
		if (r.peek() != JsonToken.BEGIN_ARRAY) {
			r.skipValue();
		}
		
		List<Trackable> list = new ArrayList<Trackable>();
		r.beginArray();
		while(r.hasNext()) {
			list.add(parse(r));
		}
		r.endArray();
		return list;
	}
	
	public static Trackable parse(JsonReader r) throws IOException {
		String guid = "";
		String travelBugName = "";
		String goal = "";
		String description = "";
		String travelBugTypeName = "";
		String travelBugTypeImage = "";
		User owner = new User("", 0, 0, new Coordinates(Double.NaN, Double.NaN), 0, false, MemberType.Guest, "", "");
		String currentCacheCode = "";
		User currentOwner = null;
		String trackingNumber = "";

		r.beginObject();
		while(r.hasNext()) {
			String name = r.nextName();
			if ("Id".equals(name)) {
				guid = r.nextString();
			} else if ("Name".equals(name)) {
				travelBugName = r.nextString();
			} else if ("CurrentGoal".equals(name)) {
				goal = r.nextString();
			} else if ("Description".equals(name)) {
				description = r.nextString();
			} else if ("TBTypeName".equals(name)) {
				travelBugTypeName = r.nextString();
			} else if ("IconUrl".equals(name)) {
				travelBugTypeImage = r.nextString();
			} else if ("OriginalOwner".equals(name)) {
				owner = parseUser(r);
			} else if ("CurrentGeocacheCode".equals(name)) {
				currentCacheCode = r.nextString();
			} else if ("CurrentOwner".equals(name)) {
				currentOwner = parseUser(r);
			} else if ("Code".equals(name)) {
				trackingNumber = r.nextString();
			} else {
				r.skipValue();
			}
		}
		r.endObject();
		
		return new Trackable(guid, travelBugName, goal, description, travelBugTypeName, travelBugTypeImage, owner, currentCacheCode, currentOwner, trackingNumber);
	}
}
