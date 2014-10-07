package com.arcao.geocaching.api.impl.live_geocaching_api.parser;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.arcao.geocaching.api.data.User;
import com.arcao.geocaching.api.data.coordinates.Coordinates;
import com.arcao.geocaching.api.data.type.AttributeType;
import com.arcao.geocaching.api.data.type.CacheType;
import com.arcao.geocaching.api.data.type.ContainerType;
import com.arcao.geocaching.api.data.type.MemberType;
import com.arcao.geocaching.api.data.type.TrackableLogType;
import com.google.gson.stream.JsonToken;

public class JsonParser {
	private static final Logger logger = LoggerFactory.getLogger(JsonParser.class);
	
	protected static Date parseJsonDate(String date) {
		Pattern DATE_PATTERN = Pattern.compile("/Date\\((-?\\d+)([-+]\\d{4})?\\)/");
		
		Matcher m = DATE_PATTERN.matcher(date);
		if (m.matches()) {
			long time = Long.parseLong(m.group(1));
			long zone = 0;
			if (m.group(2) != null && m.group(2).length() > 0)
				zone = Integer.parseInt(m.group(2)) / 100 * 1000 * 60 * 60;
			return new Date(time + zone);
		}

		logger.error("parseJsonDate failed: " + date);
		return new Date(0);
	}
	
	protected static Date parseJsonUTCDate(String date) {
		Pattern DATE_PATTERN = Pattern.compile("/Date\\((-?\\d+)([-+]\\d{4})?\\)/");
		
		Matcher m = DATE_PATTERN.matcher(date);
		if (m.matches()) {
			long time = Long.parseLong(m.group(1));
			// zone is always zero for UTC
			long zone = 0;
			return new Date(time + zone);
		}

		logger.error("parseJsonDate failed: " + date);
		return new Date(0);
	}
	
	protected static CacheType parseCacheType(JsonReader r) throws IOException {
		CacheType cacheType = CacheType.Mystery;
		
		if (isNextNull(r))
			return cacheType;
		
		r.beginObject();
		while(r.hasNext()) {
			String name = r.nextName();
			if ("GeocacheTypeId".equals(name)) {
				cacheType = CacheType.parseCacheTypeByGroundSpeakId(r.nextInt());
			} else {
				r.skipValue();
			}
		}
		r.endObject();
		return cacheType;
	}
	
	protected static ContainerType parseContainerType(JsonReader r) throws IOException {
		ContainerType containerType = ContainerType.NotChosen;
		
		if (isNextNull(r))
			return containerType;
		
		r.beginObject();
		while(r.hasNext()) {
			String name = r.nextName();
			if ("ContainerTypeId".equals(name)) {
				containerType = ContainerType.parseContainerTypeByGroundSpeakId(r.nextInt());
			} else {
				r.skipValue();
			}
		}
		r.endObject();
		return containerType;
	}
	
	protected static MemberType parseMemberType(JsonReader r) throws IOException {
		MemberType memberType = MemberType.Basic;

		if (isNextNull(r))
			return memberType;
		
		if (r.peek() == JsonToken.NUMBER) {
		  memberType = MemberType.parseMemeberTypeByGroundSpeakId(r.nextInt() / 10);
		  return memberType;
    }
		
		r.beginObject();
		while(r.hasNext()) {
			String name = r.nextName();
			if ("MemberTypeId".equals(name)) {
				memberType = MemberType.parseMemeberTypeByGroundSpeakId(r.nextInt());
			} else {
				r.skipValue();
			}
		}
		r.endObject();
		return memberType;
	}
	
	protected static Coordinates parseHomeCoordinates(JsonReader r) throws IOException {
		double latitude = Double.NaN;
		double longitude = Double.NaN;
	  
		if (isNextNull(r))
			return new Coordinates(latitude, longitude);
		
		r.beginObject();
		while(r.hasNext()) {
			String name = r.nextName();
			if ("Latitude".equals(name)) {
				latitude = (float) r.nextDouble();
			} else if ("Longitude".equals(name)) {
				longitude = (float) r.nextDouble();
			} else {
				r.skipValue();
			}
		}
		r.endObject();
		return new Coordinates(latitude, longitude);
	}
	
	protected static User parseUser(JsonReader r) throws IOException {
		if (isNextNull(r))
			return null;
		
		String avatarUrl = "";
		int findCount = 0;
		int hideCount = 0;
		Coordinates homeCoordinates = new Coordinates(Double.NaN, Double.NaN);
		long id = 0;
		boolean admin = false;
		MemberType memberType = null;
		String publicGuid = "";
		String userName = "";
		
		r.beginObject();
		while(r.hasNext()) {
			String name = r.nextName();
			if ("AvatarUrl".equals(name)) {
				avatarUrl = r.nextString();
			} else if ("FindCount".equals(name)) {
				findCount = r.nextInt();
			} else if ("HideCount".equals(name)) {
				hideCount = r.nextInt();
			} else if ("HomeCoordinates".equals(name)) {
				homeCoordinates = parseHomeCoordinates(r);
			} else if ("Id".equals(name)) {
				id = r.nextLong();
			} else if ("IsAdmin".equals(name)) {
				admin = r.nextBoolean();
			} else if ("MemberType".equals(name)) {
				memberType = parseMemberType(r);
			} else if ("PublicGuid".equals(name)) {
				publicGuid = r.nextString();
			} else if ("UserName".equals(name)) {
				userName = r.nextString();
			} else {
				r.skipValue();
			}
		}
		r.endObject();
		
		return new User(avatarUrl, findCount, hideCount, homeCoordinates, id, admin, memberType, publicGuid, userName);
	}
	
	protected static AttributeType parseAttributte(JsonReader r) throws IOException {
		int id = 1;
		boolean on = false;
		
		r.beginObject();
		while(r.hasNext()) {
			String name = r.nextName();
			if ("AttributeTypeID".equals(name)) {
				id = r.nextInt();
			} else if ("IsOn".equals(name)) {
				on = r.nextBoolean();
			} else {
				r.skipValue();
			}
		}
		r.endObject();
		
		return AttributeType.parseAttributeTypeByGroundSpeakId(id, on);
	}
	
	protected static List<AttributeType> parseAttributteList(JsonReader r) throws IOException {
		if (r.peek() != JsonToken.BEGIN_ARRAY) {
			r.skipValue();
		}
		
		List<AttributeType> list = new ArrayList<AttributeType>();
		r.beginArray();
		while(r.hasNext()) {
			list.add(parseAttributte(r));
		}
		r.endArray();
		return list;
	}
	
	protected static TrackableLogType parseTrackableLogType(JsonReader r) throws IOException {
    TrackableLogType trackableLogType = TrackableLogType.WriteNote;
    
		if (isNextNull(r))
			return trackableLogType;
    
    r.beginObject();
    while(r.hasNext()) {
      String name = r.nextName();
      if ("WptLogTypeId".equals(name)) {
        trackableLogType = TrackableLogType.parseTrackableLogTypeByGroundSpeakId(r.nextInt());
      } else {
        r.skipValue();
      }
    }
    r.endObject();
    return trackableLogType;
  }
	
	protected static boolean isNextNull(JsonReader r) throws IOException {
		if (r.peek() == JsonToken.NULL) {
			r.nextNull();
			return true;
		}
		
		return false;
	}
}
