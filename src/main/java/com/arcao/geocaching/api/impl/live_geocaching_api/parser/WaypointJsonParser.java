package com.arcao.geocaching.api.impl.live_geocaching_api.parser;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.arcao.geocaching.api.data.Waypoint;
import com.arcao.geocaching.api.data.coordinates.Coordinates;
import com.arcao.geocaching.api.data.type.WaypointType;
import com.google.gson.stream.JsonToken;

public class WaypointJsonParser extends JsonParser {
	public static List<Waypoint> parseList(JsonReader r) throws IOException {
		if (r.peek() != JsonToken.BEGIN_ARRAY) {
			r.skipValue();
		}
		
		List<Waypoint> list = new ArrayList<Waypoint>();
		r.beginArray();
		while(r.hasNext()) {
			list.add(parse(r));
		}
		r.endArray();
		return list;
	}
	
	public static Waypoint parse(JsonReader r) throws IOException {
		Coordinates.Builder coordinates = Coordinates.Builder.coordinates();
		Date time = new Date(0);
		String waypointCode = "";
		String waypointName = "";
		String note = "";
		WaypointType waypointType = WaypointType.ReferencePoint;
		
		r.beginObject();
		while(r.hasNext()) {
			String name = r.nextName();
			if ("Longitude".equals(name)) {
				coordinates.withLongitude(r.nextDouble());
			} else if ("Latitude".equals(name)) {
				coordinates.withLatitude(r.nextDouble());
			} else if ("UTCEnteredDate".equals(name)) {
				time = parseJsonUTCDate(r.nextString());
			} else if ("Code".equals(name)) {
				waypointCode = r.nextString();
			} else if ("Name".equals(name)) {
				waypointType = WaypointType.parseWayPointType(r.nextString());
			} else if ("Description".equals(name)) {
				waypointName = r.nextString();
			} else if ("Comment".equals(name)) {
				note = r.nextString();
			} else {
				r.skipValue();
			}
		}
		r.endObject();
		
		return new Waypoint(coordinates.build(), time, waypointCode, waypointName, note, waypointType);
	}
}
