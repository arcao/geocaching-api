package com.arcao.geocaching.api.impl.live_geocaching_api.parser;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.arcao.geocaching.api.data.ImageData;
import com.arcao.geocaching.api.data.Trackable;
import com.arcao.geocaching.api.data.TrackableLog;
import com.arcao.geocaching.api.data.User;
import com.google.gson.stream.JsonToken;

public class TrackableJsonParser extends JsonParser {
	private static final Logger logger = LoggerFactory.getLogger(TrackableJsonParser.class);
	
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
		long id = 0;
		String guid = "";
		String travelBugName = "";
		String goal = "";
		String description = "";
		String travelBugTypeName = "";
		String travelBugTypeImage = "";
		User owner = User.EMPTY;
		String currentCacheCode = "";
		User currentOwner = null;
		String trackingNumber = "";
		Date created = new Date(0);
		boolean allowedToBeCollected = false;
		boolean inCollection = false;
		boolean archived = false;
		
		List<TrackableLog> trackableLogs = new ArrayList<TrackableLog>();
		List<ImageData> images = new ArrayList<ImageData>();

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
			} else if ("DateCreated".equals(name)) {
				created = parseJsonDate(r.nextString());
			} else if ("AllowedToBeCollected".equals(name)) {
				allowedToBeCollected = r.nextBoolean();
			} else if ("InCollection".equals(name)) {
				inCollection = r.nextBoolean();
			} else if ("Archived".equals(name)) {
				archived = r.nextBoolean();
			} else if ("Images".equals(name)) {
				images = ImageDataJsonParser.parseList(r);
			} else if ("TrackableLogs".equals(name)) {
        trackableLogs = TrackableLogJsonParser.parseList(r);
			} else {
				r.skipValue();
			}
		}
		r.endObject();
		
		try {
			id = Long.parseLong(guid);
		} catch (NumberFormatException e) {
			logger.error("Error ocurs while converting guid to id", e);
		}
		
		return new Trackable(id, guid, travelBugName, goal, description, travelBugTypeName, travelBugTypeImage, owner, currentCacheCode, currentOwner, trackingNumber, created, allowedToBeCollected, inCollection, archived, trackableLogs, images);
	}
}
