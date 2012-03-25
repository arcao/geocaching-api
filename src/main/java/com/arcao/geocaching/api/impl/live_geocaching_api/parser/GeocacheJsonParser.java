package com.arcao.geocaching.api.impl.live_geocaching_api.parser;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.arcao.geocaching.api.data.CacheLog;
import com.arcao.geocaching.api.data.Geocache;
import com.arcao.geocaching.api.data.ImageData;
import com.arcao.geocaching.api.data.SimpleGeocache;
import com.arcao.geocaching.api.data.Trackable;
import com.arcao.geocaching.api.data.User;
import com.arcao.geocaching.api.data.UserWaypoint;
import com.arcao.geocaching.api.data.Waypoint;
import com.arcao.geocaching.api.data.coordinates.Coordinates;
import com.arcao.geocaching.api.data.type.AttributeType;
import com.arcao.geocaching.api.data.type.CacheType;
import com.arcao.geocaching.api.data.type.ContainerType;
import com.arcao.geocaching.api.data.type.MemberType;
import com.google.gson.stream.JsonToken;

public class GeocacheJsonParser extends JsonParser {
	public static List<SimpleGeocache> parseList(JsonReader r) throws IOException {
		if (r.peek() != JsonToken.BEGIN_ARRAY) {
			r.skipValue();
		}
		
		List<SimpleGeocache> list = new ArrayList<SimpleGeocache>();
		r.beginArray();
		
		while(r.hasNext()) {
			list.add(parse(r));
		}
		r.endArray();
		return list;
	}
	
	public static Geocache parse(JsonReader r) throws IOException {
		String cacheCode = "";
		String cacheName = "";
		double longitude = Double.NaN;
		double latitude = Double.NaN;
		CacheType cacheType = CacheType.Unknown;
		float difficultyRating = 1;
		float terrainRating = 1;
		User author = new User("", 0, 0, new Coordinates(Double.NaN, Double.NaN), 0, false, MemberType.Guest, "", "");
		boolean available = false;
		boolean archived = false;
		boolean premiumListing = false;
		String countryName = "";
		String stateName = "";
		Date created = new Date(0);
		String contactName = "";
		ContainerType containerType = ContainerType.NotChosen;
		int trackableCount = 0;
		boolean found = false;
		String shortDescription = "";
		String longDescription = "";
		String encodedHints = "";
		List<CacheLog> cacheLogs = new ArrayList<CacheLog>();
		List<Trackable> trackables = new ArrayList<Trackable>();
		List<Waypoint> waypoints = new ArrayList<Waypoint>();
		List<AttributeType> attributes = new ArrayList<AttributeType>();
		List<UserWaypoint> userWaypoints = new ArrayList<UserWaypoint>();
		String personalNote = "";
		List<ImageData> images = new ArrayList<ImageData>();
		
		r.beginObject();
		while(r.hasNext()) {
			String name = r.nextName();
			if ("Code".equals(name)) {
				cacheCode = r.nextString();
			} else if ("Name".equals(name)) {
				cacheName = r.nextString();
			} else if ("Longitude".equals(name)) {
				longitude = r.nextDouble();
			} else if ("Latitude".equals(name)) {
				latitude = r.nextDouble();
			} else if ("CacheType".equals(name)) {
				cacheType = parseCacheType(r);
			} else if ("Difficulty".equals(name)) {
				difficultyRating = (float) r.nextDouble();
			} else if ("Terrain".equals(name)) {
				terrainRating = (float) r.nextDouble();
			} else if ("Owner".equals(name)) {
				author = parseUser(r);
			} else if ("Available".equals(name)) {
				available = r.nextBoolean();
			} else if ("Archived".equals(name)) {
				archived = r.nextBoolean();
			} else if ("IsPremium".equals(name)) {
				premiumListing = r.nextBoolean();
			} else if ("Country".equals(name)) {
				countryName = r.nextString();
			} else if ("State".equals(name)) {
				stateName = r.nextString();
			} else if ("UTCPlaceDate".equals(name)) {
				created = JsonParser.parseJsonDate(r.nextString());
			} else if ("PlacedBy".equals(name)) {
				contactName = r.nextString();
			} else if ("ContainerType".equals(name)) {
				containerType = parseContainerType(r);
			} else if ("TrackableCount".equals(name)) {
				trackableCount = r.nextInt();
			} else if ("HasbeenFoundbyUser".equals(name)) {
				found = r.nextBoolean();
			} else if ("ShortDescription".equals(name)) {
				shortDescription = r.nextString();
			} else if ("LongDescription".equals(name)) {
				longDescription = r.nextString();
			} else if ("EncodedHints".equals(name)) {
				encodedHints = r.nextString();
			} else if ("GeocacheLogs".equals(name)) {
				cacheLogs = CacheLogJsonParser.parseList(r);
			} else if ("Trackables".equals(name)) {
				trackables = TrackableJsonParser.parseList(r);
			} else if ("AdditionalWaypoints".equals(name)) {
				waypoints = WaypointJsonParser.parseList(r);
			} else if ("Attributes".equals(name)) {
				attributes = parseAttributteList(r);
			} else if ("UserWaypoints".equals(name)) {
				userWaypoints = UserWaypointsJsonParser.parseList(r);
			} else if ("GeocacheNote".equals(name)) {
			  personalNote = r.nextString();	  
			} else if ("Images".equals(name)) {
        images = ImageDataJsonParser.parseList(r);
			} else {
				r.skipValue();
			}
		}
		r.endObject();
		
		return new Geocache(cacheCode, cacheName, new Coordinates(latitude, longitude), cacheType, difficultyRating, terrainRating, author, available, archived, premiumListing, created, contactName, containerType, trackableCount, found, countryName, stateName, shortDescription, longDescription, encodedHints, cacheLogs, trackables, waypoints, attributes, userWaypoints, personalNote, images);
	}
}
