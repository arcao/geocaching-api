package com.arcao.geocaching.api.impl.live_geocaching_api.parser;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.arcao.geocaching.api.data.UserWaypoint;
import com.arcao.geocaching.api.data.coordinates.Coordinates;
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
		Coordinates.Builder coordinates = Coordinates.Builder.coordinates();
		Date date = new Date(0);
		int userId = 0;
		boolean correctedCoordinate = false;
		
		r.beginObject();
		while(r.hasNext()) {
			String name = r.nextName();
			if ("CacheCode".equals(name)) {
				cacheCode = r.nextString();
			} else if ("Description".equals(name)) {
				description = r.nextString();
			} else if ("ID".equals(name)) {
				id = r.nextLong();
			} else if ("Latitude".equals(name)) {
				coordinates.withLatitude(r.nextDouble());
			} else if ("Longitude".equals(name)) {
				coordinates.withLongitude(r.nextDouble());
			} else if ("UTCDate".equals(name)) {
				date = parseJsonUTCDate(r.nextString());
			} else if ("UserID".equals(name)) {
				userId = r.nextInt();
			} else if ("IsCorrectedCoordinate".equals(name)) {
				correctedCoordinate = r.nextBoolean();
			} else {
				r.skipValue();
			}
		}
		r.endObject();
		
		return new UserWaypoint(cacheCode, description, id, coordinates.build(), date, userId, correctedCoordinate);
	}
}
