package com.arcao.geocaching.api.impl.live_geocaching_api.parser;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.arcao.geocaching.api.data.CacheLog;
import com.arcao.geocaching.api.data.User;
import com.arcao.geocaching.api.data.type.CacheLogType;
import com.google.gson.stream.JsonToken;

public class CacheLogJsonParser extends JsonParser {
	public static List<CacheLog> parseList(JsonReader r) throws IOException {
		if (r.peek() != JsonToken.BEGIN_ARRAY) {
			r.skipValue();
		}
		
		List<CacheLog> list = new ArrayList<CacheLog>();
		r.beginArray();
		while(r.hasNext()) {
			list.add(parse(r));
		}
		r.endArray();
		return list;
	}
	
	public static CacheLog parse(JsonReader r) throws IOException {
		Date date = new Date(0);
		CacheLogType cacheLogType = CacheLogType.Unknown;
		User author = User.EMPTY;
		String text = "";
		
		r.beginObject();
		while(r.hasNext()) {
			String name = r.nextName();
			if ("UTCCreateDate".equals(name)) {
				date = JsonParser.parseJsonDate(r.nextString());
			} else if ("LogType".equals(name)) {
				cacheLogType = parseLogType(r);
			} else if ("Finder".equals(name)) {
				author = parseUser(r);
			} else if ("LogText".equals(name)) {
				text = r.nextString();
			} else {
				r.skipValue();
			}
		}
		r.endObject();
		
		return new CacheLog(date, cacheLogType, author, text);
	}

	protected static CacheLogType parseLogType(JsonReader r) throws IOException {
		CacheLogType cacheLogType = CacheLogType.Unknown;
		
		r.beginObject();
		while(r.hasNext()) {
			String name = r.nextName();
			if ("WptLogTypeName".equals(name)) {
				cacheLogType = CacheLogType.parseLogType(r.nextString());
			} else {
				r.skipValue();
			}
		}
		r.endObject();
		return cacheLogType;
	}
}
