package com.arcao.geocaching.api.impl.live_geocaching_api.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.arcao.geocaching.api.data.GeocacheLog;
import com.arcao.geocaching.api.data.coordinates.Coordinates;
import com.arcao.geocaching.api.data.type.GeocacheLogType;
import com.google.gson.stream.JsonToken;

public class GeocacheLogJsonParser extends JsonParser {
	public static List<GeocacheLog> parseList(JsonReader r) throws IOException {
		if (r.peek() != JsonToken.BEGIN_ARRAY) {
			r.skipValue();
		}

		List<GeocacheLog> list = new ArrayList<GeocacheLog>();
		r.beginArray();
		while (r.hasNext()) {
			list.add(parse(r));
		}
		r.endArray();
		return list;
	}

	public static GeocacheLog parse(JsonReader r) throws IOException {
		GeocacheLog.Builder geocacheLog = GeocacheLog.Builder.geocacheLog();
		Coordinates.Builder updatedCoordinates = Coordinates.Builder.coordinates();

		r.beginObject();
		while (r.hasNext()) {
			String name = r.nextName();
			if ("ID".equals(name)) {
				geocacheLog.withId(r.nextLong());
			} else if ("CacheCode".equals(name)) {
				geocacheLog.withCacheCode(r.nextString());
			} else if ("UTCCreateDate".equals(name)) {
				geocacheLog.withCreated(JsonParser.parseJsonUTCDate(r.nextString()));
			} else if ("VisitDate".equals(name)) {
				geocacheLog.withVisited(JsonParser.parseJsonDate(r.nextString()));
			} else if ("LogType".equals(name)) {
				geocacheLog.withCacheLogType(parseLogType(r));
			} else if ("Finder".equals(name)) {
				geocacheLog.withAuthor(parseUser(r));
			} else if ("LogText".equals(name)) {
				geocacheLog.withText(r.nextString());
			} else if ("Images".equals(name)) {
				geocacheLog.withImages(ImageDataJsonParser.parseList(r));
			} else if ("UpdatedLatitude".equals(name)) {
				updatedCoordinates.withLatitude(r.nextDouble());
			} else if ("UpdatedLongitude".equals(name)) {
				updatedCoordinates.withLongitude(r.nextDouble());
			} else if ("IsApproved".equals(name)) {
				geocacheLog.withApproved(r.nextBoolean());
			} else if ("IsArchived".equals(name)) {
				geocacheLog.withArchived(r.nextBoolean());
			} else if ("CannotDelete".equals(name)) {
				geocacheLog.withUndeletable(r.nextBoolean());
			} else {
				r.skipValue();
			}
		}
		r.endObject();

		geocacheLog.withUpdatedCoordinates(updatedCoordinates.build());

		return geocacheLog.build();
	}

	protected static GeocacheLogType parseLogType(JsonReader r) throws IOException {
		GeocacheLogType geocacheLogType = null;
		
		if (isNextNull(r))
			return geocacheLogType;

		r.beginObject();
		while (r.hasNext()) {
			String name = r.nextName();
			if ("WptLogTypeName".equals(name)) {
				geocacheLogType = GeocacheLogType.getByName(r.nextString());
			} else {
				r.skipValue();
			}
		}
		r.endObject();
		return geocacheLogType;
	}
}
