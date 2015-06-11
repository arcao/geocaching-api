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
		Trackable.Builder trackable = Trackable.Builder.trackable();

		r.beginObject();
		while(r.hasNext()) {
			String name = r.nextName();
			if ("Id".equals(name)) {
				trackable.withId(r.nextLong());
			} else if ("Name".equals(name)) {
				trackable.withName(r.nextString());
			} else if ("CurrentGoal".equals(name)) {
				trackable.withGoal(r.nextString());
			} else if ("Description".equals(name)) {
				trackable.withDescription(r.nextString());
			} else if ("TBTypeName".equals(name)) {
				trackable.withTrackableTypeName(r.nextString());
			} else if ("IconUrl".equals(name)) {
				trackable.withTrackableTypeImage(r.nextString());
			} else if ("OriginalOwner".equals(name)) {
				trackable.withOwner(parseUser(r));
			} else if ("CurrentGeocacheCode".equals(name)) {
				trackable.withCurrentCacheCode(r.nextString());
			} else if ("CurrentOwner".equals(name)) {
				trackable.withCurrentOwner(parseUser(r));
			} else if ("Code".equals(name)) {
				trackable.withTrackingNumber(r.nextString());
			} else if ("DateCreated".equals(name)) {
				trackable.withCreated(parseJsonDate(r.nextString()));
			} else if ("AllowedToBeCollected".equals(name)) {
				trackable.withAllowedToBeCollected(r.nextBoolean());
			} else if ("InCollection".equals(name)) {
				trackable.withInCollection(r.nextBoolean());
			} else if ("Archived".equals(name)) {
				trackable.withArchived(r.nextBoolean());
			} else if ("Images".equals(name)) {
				trackable.withImages(ImageDataJsonParser.parseList(r));
			} else if ("TrackableLogs".equals(name)) {
        trackable.withTrackableLogs(TrackableLogJsonParser.parseList(r));
			} else {
				r.skipValue();
			}
		}
		r.endObject();

		return trackable.build();
	}
}
