package com.arcao.geocaching.api.impl.live_geocaching_api.parser;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.arcao.geocaching.api.data.UserWaypoint;
import com.google.gson.stream.JsonToken;

public class UserWaypointsJsonParser extends JsonParser { 
	public static List<UserWaypoint> parseList(JsonReader r) throws IOException {
		if (r.peek() != JsonToken.BEGIN_ARRAY) {
			r.skipValue();
		}
		
		List<UserWaypoint> list = new ArrayList<UserWaypoint>();
		r.beginArray();
		while(r.hasNext()) {
			list.add(parse(r));
		}
		r.endArray();
		return list;
	}
	
	public static UserWaypoint parse(JsonReader r) throws IOException {
		String cacheCode = "";
		String description = "";
		long id = 0;
		double latitude = Double.NaN;
		double longitude = Double.NaN;
		Date date = new Date(0);
		int userId = 0;
		
		r.beginObject();
		while(r.hasNext()) {
			String name = r.nextName();
			if ("CacheCode".equals(name)) {
				cacheCode = r.nextString();
			} else if ("Description".equals(name)) {
				description = r.nextString();
			} else if ("Id".equals(name)) {
				id = r.nextLong();
			} else if ("Latitude".equals(name)) {
				latitude = r.nextDouble();
			} else if ("Longitude".equals(name)) {
				longitude = r.nextDouble();
			} else if ("UTCDate".equals(name)) {
				date = parseJsonDate(r.nextString());
			} else if ("UserId".equals(name)) {
				userId = r.nextInt();
			} else {
				r.skipValue();
			}
		}
		r.endObject();
		
		return new UserWaypoint(cacheCode, description, id, latitude, longitude, date, userId);
	}
}